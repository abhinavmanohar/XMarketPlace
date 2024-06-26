package com.xmarketplace.service;

import com.google.gson.JsonObject;
import com.xmarketplace.DTO.AccountDetailsDTO;
import com.xmarketplace.DTO.ProductListingDTO;
import com.xmarketplace.DTO.TransactionDTO;
import com.xmarketplace.Entity.ProductListing;
import com.xmarketplace.Entity.Transactions;
import com.xmarketplace.Entity.User;
import com.xmarketplace.Mapper.TransactionMapper;
import com.xmarketplace.Repository.TransactionsRepository;
import com.xmarketplace.util.ActionEnum;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TransactionsService {
    private TransactionsRepository transactionRepository;

    @Autowired
    private ProductListingService productListingService;

    @Autowired
    private UserService userService;

    public TransactionsService(TransactionsRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public boolean isUserValidToFetchListing(Integer userId, String action) {
        // user validation
        if(transactionRepository.findLatestByUserIdAndAction(userId, action).isPresent()) {
            Date transactionDate = (TransactionMapper.entityToDto(transactionRepository.findLatestByUserIdAndAction(userId, action).get()).getCreatedAt());
            Date currentDate = new Date();
            long diffInMillies = Math.abs(currentDate.getTime() - transactionDate.getTime());
            long diffInDays = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diffInDays >= 24) {
                return true;
            }
            return false;
        }else if(userService.GetUserById(userId).isPresent()) {
            return true;
        }
        return false;
    }

    public boolean isUserValidToPurchaseProduct(Integer userId, String action) {
        // user validation
        if(transactionRepository.findLatestByUserIdAndAction(userId, action).isPresent()) {
            Date transactionDate = (TransactionMapper.entityToDto(transactionRepository.findLatestByUserIdAndAction(userId, action).get()).getCreatedAt());
            Date currentDate = new Date();
            long diffInMillies = Math.abs(currentDate.getTime() - transactionDate.getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diffInDays >= 30) {
                return true;
            }
            return false;
        }else if(userService.GetUserById(userId).isPresent()) {
            return true;
        }
        return false;
    }

    @Transactional
    public boolean purchaseProduct(Integer userId, Integer productId) throws Exception {
        //check last transaction of the user
        if(!userService.GetUserById(userId).isPresent()){
            throw new Exception("User Id not available");
        }
        User buyer = userService.GetUserById(userId).get();
        if (!isUserValidToPurchaseProduct(userId, "BUY") ){
            throw new Exception("User is not eligible to purchase the product, Please try after 30 days");
        }
        if (!productListingService.isQuantityAvailable(productId)) {
            throw new Exception("Product is not available");
        }
            ReentrantLock lock = new ReentrantLock();
            lock.lock();
            ProductListing productListing = productListingService.getProductListingEntityById(productId);
            if (productListing ==  null) {
                throw new Exception("Invalid Product Id");
            }
            double productAmount = productListing.getPrice();
            // payment is successful to Supaki
            if (!userService.updateUserWallet(getSUPAKIUserId(), productAmount)) {
                throw new Exception("Payment failed");
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("transaction_amount", productAmount);
            TransactionDTO transactionDTO = new TransactionDTO(buyer.getUserId(), productId, ActionEnum.BUY.toString(), new Date(), jsonObject.toString());
            save(TransactionMapper.dtoToEntity(transactionDTO,buyer,productListing));

        if (userService.GetUserById(productListing.getUser().getUserId()).isPresent()){
                double amountPaidToSupaki = productListing.getPrice() * 0.1;
                double amountPaidToSeller = productListing.getPrice() * 0.9;
                User seller = userService.GetUserById(productListing.getUser().getUserId()).get();
                if (!userService.updateUserWallet(getSUPAKIUserId(), -amountPaidToSeller)) {
                    throw new Exception("Payment failed");
                }
                //payment is successful to seller
                if(!userService.updateUserWallet(seller.getUserId(), amountPaidToSeller)){
                    throw new Exception("Payment failed");
                }
                JsonObject settlementObject = new JsonObject();
                settlementObject.addProperty("settlement_amount", amountPaidToSeller);
                settlementObject.addProperty("commission_amount", amountPaidToSupaki);
                settlementObject.addProperty("settlement_transferred_to",seller.getUserId());
                TransactionDTO settlement = new TransactionDTO(getSUPAKIUserId(), productId, ActionEnum.SETTLEMENT_TRANSFER.toString(), new Date(), settlementObject.toString());
                save(TransactionMapper.dtoToEntity(settlement,userService.GetUserById(getSUPAKIUserId()).get(),productListing));
                // update quantity of product
                productListingService.updateProductListing(productListing);

                lock.unlock();
            }else{
                throw new Exception("User Id not available");
            }

        return false;
    }

    public int getSUPAKIUserId() {
        return 1;
    }

    public void save(Transactions transactions) {
        transactionRepository.save(transactions);
    }

    public Optional<List<Transactions>> getTransactionsByUserId(Integer userId) {
            return transactionRepository.findAllByUserId(userId);
    }
    public List<AccountDetailsDTO> getUserTransactionDetails(Integer userId) {
        if(getTransactionsByUserId(userId).isPresent() &&
                !getTransactionsByUserId(userId).get().isEmpty()){
            return TransactionMapper.transactionToAccountDetails(getTransactionsByUserId(userId).get());
        }
        return Collections.emptyList();
    }
    public List<Transactions> getAllTransactionDetails() {
        if(transactionRepository.findAll().isEmpty()){
            return Collections.emptyList();
        }
        return transactionRepository.findAll();
    }

}

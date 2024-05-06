package com.xmarketplace.service;

import com.xmarketplace.DTO.ProductListingDTO;
import com.xmarketplace.DTO.TransactionDTO;
import com.xmarketplace.Entity.ProductListing;
import com.xmarketplace.Entity.User;
import com.xmarketplace.Mapper.ProductListingMapper;
import com.xmarketplace.Mapper.TransactionMapper;
import com.xmarketplace.Repository.ProductListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.ActionEnum;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProductListingService {

    private ProductListingRepository productListingRepository;

    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private UserService userService;

    public ProductListingService(ProductListingRepository productListingRepository) {
        this.productListingRepository = productListingRepository;
    }

    public void addProductListing(Integer userId,ProductListingDTO productListingDTO) throws Exception {
      ProductListing productListing=ProductListingMapper.dtoToEntity(productListingDTO);
        // validation if buyer is eligible to view the product listing
        // check for 24 hours time duaration
        // get the latest transaction of the user
        if (userService.GetUserById(userId).isPresent()){
        User seller = userService.GetUserById(userId).get();
        if (!transactionsService.isUserValidToFetchListing(seller.getUserId(), ActionEnum.BUY.toString())){
            throw new Exception("User is not eligible to Add the product listing, Please try after 24 hours");
        }
        if (!isValidProductPrice(productListingDTO)) {
            throw new Exception("Product price should be between 10 and 1000");
        }
        productListing.setUser(seller);
        productListing = productListingRepository.save(productListing);
        TransactionDTO transactionDTO = new TransactionDTO(userId, productListing.getId(), ActionEnum.SELL.toString(), new Date());
        transactionsService.save(TransactionMapper.dtoToEntity(transactionDTO,seller,productListing));
      }else{
          throw new Exception("User Id not available");
      }
    }

    public boolean isValidProductPrice(ProductListingDTO productListingDTO) {
        return productListingDTO.getPrice() >= 10 && productListingDTO.getPrice() <= 1000;
    }

    public List<ProductListingDTO> getAllProductListings(Integer userId) {
        return ProductListingMapper.entityToDto(productListingRepository.findAll());
    }

    public ProductListingDTO getProductListingById(Integer productId) {
        if(productListingRepository.findById(productId).isPresent()){
            return ProductListingMapper.entityToDto(productListingRepository.findById(productId).get());
        }
        return null;
    }
    public ProductListing getProductListingEntityById(Integer productId) {
        if(productListingRepository.findById(productId).isPresent()){
            return productListingRepository.findById(productId).get();
        }
        return null;
    }
    public boolean isQuantityAvailable(Integer productId) {
        return getProductListingById(productId) !=null ? getProductListingById(productId).getQuantity() > 0 : false;
    }

    public void updateProductListing(ProductListing productListing) throws Exception {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        if (productListingRepository.getById(productListing.getId()).getQuantity() <= 0) {
            throw new Exception("Product is out of stock");
        }
        productListing.setQuantity(productListing.getQuantity()-1);
        productListingRepository.save(productListing);
        lock.unlock();
    }
}

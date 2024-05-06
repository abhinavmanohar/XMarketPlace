package com.xmarketplace.Mapper;


import com.xmarketplace.DTO.AccountDetailsDTO;
import com.xmarketplace.DTO.TransactionDTO;
import com.xmarketplace.Entity.ProductListing;
import com.xmarketplace.Entity.Transactions;
import com.xmarketplace.Entity.User;

import java.util.ArrayList;
import java.util.List;

public class TransactionMapper {

    public static Transactions dtoToEntity(TransactionDTO dto, User user, ProductListing productListing) {
        Transactions entity = new Transactions();
        if (dto.getId() != null) {
            entity.setTransactionId(dto.getId());
        }
        entity.setAction(dto.getAction());
        entity.setUser(user);
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setProductListing(productListing);
        return entity;
    }

    public static TransactionDTO entityToDto(Transactions entity) {
        TransactionDTO dto = new TransactionDTO(entity.getUser().getUserId(), entity.getProductListing().getId(), entity.getAction(),entity.getCreatedAt());
        dto.setId(entity.getTransactionId());
        return dto;
    }

    public static List<AccountDetailsDTO> transactionToAccountDetails(List<Transactions> transactions){
        List<AccountDetailsDTO> accountDetailsDTOs = new ArrayList<>();
        transactions.stream().forEach(transaction -> {
            AccountDetailsDTO accountDetailsDTO = new AccountDetailsDTO(
                    transaction.getTransactionId(),
                    transaction.getCreatedAt(),
                    transaction.getAction(),
                    transaction.getUser().getWalletBalance(),
                    transaction.getProductListing().getPrice(),
                    transaction.getProductListing().getName()
            );
            accountDetailsDTOs.add(accountDetailsDTO);
        });
        return accountDetailsDTOs;
    }
}

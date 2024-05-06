package com.xmarketplace.controller;

import com.xmarketplace.DTO.AccountDetailsDTO;
import com.xmarketplace.DTO.ProductListingDTO;
import com.xmarketplace.DTO.TransactionDTO;
import com.xmarketplace.Entity.ProductListing;

import com.xmarketplace.service.TransactionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.xmarketplace.service.ProductListingService;

import java.util.List;


@RestController
public class SystemController {

    private ProductListingService productListingService;
    private TransactionsService transactionsService;

    public SystemController(ProductListingService productListingService, TransactionsService transactionsService) {
        this.productListingService = productListingService;
        this.transactionsService = transactionsService;
    }

    @PostMapping("v1/product-listings")
    public ResponseEntity<String> saveProductListing(@RequestHeader("user_id") Integer userId, @RequestBody ProductListingDTO productListingDTO) throws Exception {
        productListingDTO.setName(productListingDTO.getName());
        productListingDTO.setPrice(productListingDTO.getPrice());
        productListingDTO.setQuantity(productListingDTO.getQuantity());
        productListingService.addProductListing(userId,productListingDTO);
        return new ResponseEntity<>("Product Listing created successfully",HttpStatus.CREATED);
    }

    @GetMapping("v1/product-listings")
    public ResponseEntity<List<ProductListingDTO>> getProductListing(@RequestHeader("user_id") Integer userId) {
        List<ProductListingDTO> productListingDTOList = productListingService.getAllProductListings(userId);
        return new ResponseEntity<>(productListingDTOList, HttpStatus.OK);
    }

    @PostMapping("v1/purchase")
    public ResponseEntity<String> purchaseProduct( @RequestBody TransactionDTO transactionDTO) throws Exception {
        transactionsService.purchaseProduct(transactionDTO.getUserId(),transactionDTO.getProductId());
        return new ResponseEntity<>("Transaction successfully completed for requested product purchase",HttpStatus.CREATED);
    }

    @GetMapping("v1/account/details")
    public ResponseEntity<List<AccountDetailsDTO>> getAccountDetails( @RequestParam Integer userId) throws Exception {
        List<AccountDetailsDTO> accountDetailsDTOList = transactionsService.getUserTransactionDetails(userId);
        return new ResponseEntity<>(accountDetailsDTOList,HttpStatus.OK);
    }
}

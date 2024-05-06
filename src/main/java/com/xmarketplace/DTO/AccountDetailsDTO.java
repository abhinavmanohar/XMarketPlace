package com.xmarketplace.DTO;

import java.util.Date;

public class AccountDetailsDTO {
    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Double getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(Double walletAmount) {
        this.walletAmount = walletAmount;
    }

    public Double getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Double productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private Integer transactionId;
    private Date createdAt;
    private String action;
    private Double walletAmount;
    private Double productAmount;
    private String productName;
    private String userName;

    public AccountDetailsDTO(Integer transactionId, Date createdAt, String action, Double walletAmount, Double productAmount, String productName, String userName) {
        this.transactionId = transactionId;
        this.createdAt = createdAt;
        this.action = action;
        this.walletAmount = walletAmount;
        this.productAmount = productAmount;
        this.productName = productName;
        this.userName = userName;
    }

}

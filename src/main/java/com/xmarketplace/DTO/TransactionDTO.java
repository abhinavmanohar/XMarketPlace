package com.xmarketplace.DTO;

import java.util.Date;

public class TransactionDTO {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private String action;
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }



    public TransactionDTO(Integer userId, Integer productId, String action, Date createdAt) {
        this.userId = userId;
        this.productId = productId;
        this.action = action;
        this.createdAt=createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

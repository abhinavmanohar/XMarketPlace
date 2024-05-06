package com.xmarketplace.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(length = 50)
    private String user_name;

    private double wallet_balance;

    @Column(columnDefinition = "json")
    private String metadata;

    // Getters and Setters
    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public double getWalletBalance() {
        return wallet_balance;
    }

    public void setWalletBalance(double wallet_balance) {
        this.wallet_balance = wallet_balance;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
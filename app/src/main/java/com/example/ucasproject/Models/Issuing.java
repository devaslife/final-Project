package com.example.ucasproject.Models;

import java.io.Serializable;
import java.util.Date;

public class Issuing implements Serializable {
    private String id;
    private String userName;
    private String userNo;
    private String date;
    private double balance;
    private double price;

    public Issuing() {
    }

    public Issuing(String userName, String userNo, String date, double price) {
        this.userName = userName;
        this.userNo = userNo;
        this.date = date;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

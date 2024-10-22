package com.cogent.entity;

import javax.xml.crypto.Data;
import java.util.Date;

public class Transaction {
    private int transactionId;
    private int userId;
    private String action;
    private Date timestamp;

    public Transaction(int transactionId, int userId, String action, Date timestamp) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.action = action;
        this.timestamp = timestamp;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

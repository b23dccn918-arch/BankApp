package com.mycompany.bankonline.Model;

import javafx.beans.property.*;
import java.sql.Timestamp;

public class Transaction {

    private final Long transactionId;
    private final String fromAccount;
    private final String toAccount;
    private final String type;
    private final Double amount;
    private final String description;
    private final Timestamp createdAt;
    private final String status;

    public Transaction(long id, String from, String to, String type,
                       double amount, String description, Timestamp createdAt, String status) {
        this.transactionId = id;
        this.fromAccount = from;
        this.toAccount = to;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public String getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status;
    }

    
}

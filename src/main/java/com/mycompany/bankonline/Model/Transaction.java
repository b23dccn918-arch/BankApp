package com.mycompany.bankonline.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private long transactionId;
    private int fromUserId;
    private Integer toUserId; // có thể null
    private String type;      // "deposit" | "withdraw" | "transfer" | "payment"
    private BigDecimal amount;
    private String description;
    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private LocalDateTime createdAt;
    private String status;    // "pending" | "success" | "failed"

    public Transaction() {}

    public Transaction(long transactionId, int fromUserId, Integer toUserId,
                       String type, BigDecimal amount, String description,
                       LocalDateTime createdAt, String status) {
        this.transactionId = transactionId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
        this.status = status;
    }

    // Getter và Setter
    // ...
}


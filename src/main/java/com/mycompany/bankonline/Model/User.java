package com.mycompany.bankonline.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class User {
    private int userId;
    private String fullName;
    private String phone;
    private String citizenIdentifier;
    private String job;
    private String address;
    private String email;
    private String passwordHash;
    private String accountType; // "checking" | "saving"
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private String status; // "active" | "blocked"

    // Có thể có quan hệ với Card, Transaction
    private List<Card> cards;
    private List<Transaction> sentTransactions;
    private List<Transaction> receivedTransactions;

    public User() {}

    public User(int userId, String fullName, String phone, String citizenIdentifier,
                String job, String address, String email, String passwordHash,
                String accountType, BigDecimal balance, LocalDateTime createdAt, String status) {
        this.userId = userId;
        this.fullName = fullName;
        this.phone = phone;
        this.citizenIdentifier = citizenIdentifier;
        this.job = job;
        this.address = address;
        this.email = email;
        this.passwordHash = passwordHash;
        this.accountType = accountType;
        this.balance = balance;
        this.createdAt = createdAt;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCitizenIdentifier() {
        return citizenIdentifier;
    }

    public void setCitizenIdentifier(String citizenIdentifier) {
        this.citizenIdentifier = citizenIdentifier;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Transaction> getSentTransactions() {
        return sentTransactions;
    }

    public void setSentTransactions(List<Transaction> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }

    public List<Transaction> getReceivedTransactions() {
        return receivedTransactions;
    }

    public void setReceivedTransactions(List<Transaction> receivedTransactions) {
        this.receivedTransactions = receivedTransactions;
    }

    // Getter và Setter
    // ...
}

package com.mycompany.bankonline.Model;

import java.time.LocalDate;

public class Card {
    private int cardId;
    private int userId;
    private String cardNumber;
    private String cardType;   // "Debit" | "Credit"
    private LocalDate expiredDate;
    private String cvv;        // demo
    private String status;     // "active" | "blocked" | "expired"

    public Card() {}

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Card(int cardId, int userId, String cardNumber, String cardType,
                LocalDate expiredDate, String cvv, String status) {
        this.cardId = cardId;
        this.userId = userId;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.expiredDate = expiredDate;
        this.cvv = cvv;
        this.status = status;
    }

}


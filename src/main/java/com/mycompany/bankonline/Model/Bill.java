package com.mycompany.bankonline.Model;

import javafx.beans.property.*;

public class Bill {
    private final long billId;
    private final String billType;
    private final Double amount;
    private final String status;
    private final String dueDate;
    private final String paidAt;
    private final String createdAt;

    public Bill(long billId, String billType, double amount, String status, String dueDate,String paidAt ,String createdAt) {
        this.billId = billId;
        this.billType = billType;
        this.amount = amount;
        this.status = status;
        this.dueDate = dueDate;
        this.paidAt = paidAt;
        this.createdAt = createdAt;
    }

    

    private Bill(Builder builder) {
        this.billId = builder.billId;
        this.billType = builder.billType;
        this.amount = builder.amount;
        this.status = builder.status;
        this.dueDate = builder.dueDate;
        this.paidAt = builder.payAt;
        this.createdAt = builder.createdAt;
    }

    
    public long getBillId() {
        return billId;
    }



    public String getBillType() {
        return billType;
    }



    public Double getAmount() {
        return amount;
    }



    public String getStatus() {
        return status;
    }



    public String getDueDate() {
        return dueDate;
    }



    public String getPaidAt() {
        return paidAt;
    }



    public String getCreatedAt() {
        return createdAt;
    }


    public static class Builder {
        private long billId;
        private String billType;
        private double amount;
        private String status;
        private String dueDate;
        private String createdAt;
        private String payAt;
        public Builder(){
            
        }

        public Builder billId(long billId) {
            this.billId = billId;
            return this;
        }

        public Builder billType(String billType) {
            this.billType = billType;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder dueDate(String dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder payAt(String payAt) {
            this.payAt = payAt;
            return this;
        }

        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Bill build() {
            return new Bill(this);
        }
    }
}

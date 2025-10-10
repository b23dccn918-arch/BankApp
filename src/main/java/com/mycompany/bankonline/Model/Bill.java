package com.mycompany.bankonline.Model;

import javafx.beans.property.*;

public class Bill {
    private final StringProperty billId;
    private final StringProperty billType;
    private final DoubleProperty amount;
    private final StringProperty status;
    private final StringProperty dueDate;
    private final StringProperty createdAt;

    public Bill(long billId, String billType, double amount, String status, String dueDate, String createdAt) {
        this.billId = new SimpleStringProperty(String.valueOf(billId));
        this.billType = new SimpleStringProperty(billType);
        this.amount = new SimpleDoubleProperty(amount);
        this.status = new SimpleStringProperty(status);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.createdAt = new SimpleStringProperty(createdAt);
    }

    private Bill(Builder builder) {
        this.billId = new SimpleStringProperty(String.valueOf(builder.billId));
        this.billType = new SimpleStringProperty(builder.billType);
        this.amount = new SimpleDoubleProperty(builder.amount);
        this.status = new SimpleStringProperty(builder.status);
        this.dueDate = new SimpleStringProperty(builder.dueDate);
        this.createdAt = new SimpleStringProperty(builder.createdAt);
    }

    public StringProperty billIdProperty() { return billId; }
    public StringProperty billTypeProperty() { return billType; }
    public DoubleProperty amountProperty() { return amount; }
    public StringProperty statusProperty() { return status; }
    public StringProperty dueDateProperty() { return dueDate; }
    public StringProperty createdAtProperty() { return createdAt; }
    public static class Builder {
        private long billId;
        private String billType;
        private double amount;
        private String status;
        private String dueDate;
        private String createdAt;

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

        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Bill build() {
            return new Bill(this);
        }
    }
}

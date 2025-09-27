package com.mycompany.bankonline.History;

import javafx.beans.property.*;

public class Transaction {
    private final LongProperty transactionId;
    private final StringProperty fromUser;
    private final StringProperty toUser;
    private final StringProperty type;
    private final DoubleProperty amount;
    private final StringProperty description;
    private final StringProperty createdAt;
    private final StringProperty status;

    public Transaction(long transactionId,
                       String fromUser,
                       String toUser,
                       String type,
                       double amount,
                       String description,
                       String createdAt,
                       String status) {
        this.transactionId = new SimpleLongProperty(transactionId);
        this.fromUser = new SimpleStringProperty(fromUser);
        this.toUser = new SimpleStringProperty(toUser != null ? toUser : "—");
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleDoubleProperty(amount);
        this.description = new SimpleStringProperty(description);
        this.createdAt = new SimpleStringProperty(createdAt);
        this.status = new SimpleStringProperty(status);
    }

    // Getters (property)
    public LongProperty transactionIdProperty() { return transactionId; }
    public StringProperty fromUserProperty() { return fromUser; }
    public StringProperty toUserProperty() { return toUser; }
    public StringProperty typeProperty() { return type; }
    public DoubleProperty amountProperty() { return amount; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty createdAtProperty() { return createdAt; }
    public StringProperty statusProperty() { return status; }

    // Convenience Getters
    public long getTransactionId() { return transactionId.get(); }
    public String getFromUser() { return fromUser.get(); }
    public String getToUser() { return toUser.get(); }
    public String getType() { return type.get(); }
    public double getAmount() { return amount.get(); }
    public String getDescription() { return description.get(); }
    public String getCreatedAt() { return createdAt.get(); }
    public String getStatus() { return status.get(); }

    // Setters
    public void setTransactionId(long value) { transactionId.set(value); }
    public void setFromUser(String value) { fromUser.set(value); }
    public void setToUser(String value) { toUser.set(value); }
    public void setType(String value) { type.set(value); }
    public void setAmount(double value) { amount.set(value); }
    public void setDescription(String value) { description.set(value); }
    public void setCreatedAt(String value) { createdAt.set(value); }
    public void setStatus(String value) { status.set(value); }
}

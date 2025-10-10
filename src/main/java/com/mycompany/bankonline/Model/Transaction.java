package com.mycompany.bankonline.Model;

import javafx.beans.property.*;
import java.sql.Timestamp;

public class Transaction {

    private final LongProperty transactionId;
    private final StringProperty fromAccount;
    private final StringProperty toAccount;
    private final StringProperty type;
    private final DoubleProperty amount;
    private final StringProperty description;
    private final ObjectProperty<Timestamp> createdAt;
    private final StringProperty status;

    public Transaction(long id, String from, String to, String type,
                       double amount, String description, Timestamp createdAt, String status) {
        this.transactionId = new SimpleLongProperty(id);
        this.fromAccount = new SimpleStringProperty(from);
        this.toAccount = new SimpleStringProperty(to);
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleDoubleProperty(amount);
        this.description = new SimpleStringProperty(description);
        this.createdAt = new SimpleObjectProperty<>(createdAt);
        this.status = new SimpleStringProperty(status);
    }

    public LongProperty transactionIdProperty() { return transactionId; }
    public StringProperty fromAccountProperty() { return fromAccount; }
    public StringProperty toAccountProperty() { return toAccount; }
    public StringProperty typeProperty() { return type; }
    public DoubleProperty amountProperty() { return amount; }
    public StringProperty descriptionProperty() { return description; }
    public ObjectProperty<Timestamp> createdAtProperty() { return createdAt; }
    public StringProperty statusProperty() { return status; }
}

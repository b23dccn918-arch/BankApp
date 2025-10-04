package com.mycompany.bankonline.History;

import javafx.beans.property.*;

public class TransactionView {
    private final LongProperty transactionId;
    private final StringProperty fromUser;
    private final StringProperty toUser;
    private final StringProperty type;
    private final DoubleProperty amount;
    private final StringProperty description;
    private final StringProperty createdAt;
    private final StringProperty status;

    public TransactionView(Long transactionId, String fromUser, String toUser,
                       String type, Double amount, String description,
                       String createdAt, String status) {
        this.transactionId = new SimpleLongProperty(transactionId);
        this.fromUser = new SimpleStringProperty(fromUser);
        this.toUser = new SimpleStringProperty(toUser != null ? toUser : ""); // nếu null thì để rỗng
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleDoubleProperty(amount);
        this.description = new SimpleStringProperty(description);
        this.createdAt = new SimpleStringProperty(createdAt);
        this.status = new SimpleStringProperty(status);
    }

    // Getter cho TableView binding
    public LongProperty transactionIdProperty() { return transactionId; }
    public StringProperty fromUserProperty() { return fromUser; }
    public StringProperty toUserProperty() { return toUser; }
    public StringProperty typeProperty() { return type; }
    public DoubleProperty amountProperty() { return amount; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty createdAtProperty() { return createdAt; }
    public StringProperty statusProperty() { return status; }

    // Getter/Setter tiện dụng (nếu cần lấy giá trị)
    public long getTransactionId() { return transactionId.get(); }
    public void setTransactionId(long id) { this.transactionId.set(id); }

    public String getFromUser() { return fromUser.get(); }
    public void setFromUser(String from) { this.fromUser.set(from); }

    public String getToUser() { return toUser.get(); }
    public void setToUser(String to) { this.toUser.set(to); }

    public String getType() { return type.get(); }
    public void setType(String type) { this.type.set(type); }

    public double getAmount() { return amount.get(); }
    public void setAmount(double amount) { this.amount.set(amount); }

    public String getDescription() { return description.get(); }
    public void setDescription(String desc) { this.description.set(desc); }

    public String getCreatedAt() { return createdAt.get(); }
    public void setCreatedAt(String date) { this.createdAt.set(date); }

    public String getStatus() { return status.get(); }
    public void setStatus(String st) { this.status.set(st); }
}

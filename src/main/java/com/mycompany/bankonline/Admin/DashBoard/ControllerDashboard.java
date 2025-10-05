package com.mycompany.bankonline.Admin.DashBoard;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

public class ControllerDashboard {
     @FXML
    private StackPane contentArea;

    // Logout button action
    @FXML
    private void handleLogout() {
        System.out.println("Logout clicked!");
        // TODO: chuyển về màn hình login
    }

    // Show Users Table
    @FXML
    private void showUsers() {
        TableView<User> table = new TableView<>();

        TableColumn<User, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<User, String> nameCol = new TableColumn<>("Full Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<User, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<User, Double> balanceCol = new TableColumn<>("Balance");
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));

        table.getColumns().addAll(idCol, nameCol, phoneCol, balanceCol);

        contentArea.getChildren().setAll(table);
    }

    // Show Cards Table
    @FXML
    private void showCards() {
        TableView<Card> table = new TableView<>();

        TableColumn<Card, Integer> idCol = new TableColumn<>("Card ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("cardId"));

        TableColumn<Card, String> numCol = new TableColumn<>("Card Number");
        numCol.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));

        TableColumn<Card, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("cardType"));

        TableColumn<Card, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(idCol, numCol, typeCol, statusCol);

        contentArea.getChildren().setAll(table);
    }

    // Show Transactions Table
    @FXML
    private void showTransactions() {
        TableView<Transaction> table = new TableView<>();

        TableColumn<Transaction, Long> idCol = new TableColumn<>("Txn ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));

        TableColumn<Transaction, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Transaction, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(idCol, typeCol, amountCol, statusCol);

        contentArea.getChildren().setAll(table);
    }

    // --- Inner classes demo (bạn sẽ thay bằng model thực tế) ---
    public static class User {
        private Integer userId;
        private String fullName;
        private String phone;
        private Double balance;

        public User(Integer userId, String fullName, String phone, Double balance) {
            this.userId = userId;
            this.fullName = fullName;
            this.phone = phone;
            this.balance = balance;
        }

        public Integer getUserId() { return userId; }
        public String getFullName() { return fullName; }
        public String getPhone() { return phone; }
        public Double getBalance() { return balance; }
    }

    public static class Card {
        private Integer cardId;
        private String cardNumber;
        private String cardType;
        private String status;

        public Card(Integer cardId, String cardNumber, String cardType, String status) {
            this.cardId = cardId;
            this.cardNumber = cardNumber;
            this.cardType = cardType;
            this.status = status;
        }

        public Integer getCardId() { return cardId; }
        public String getCardNumber() { return cardNumber; }
        public String getCardType() { return cardType; }
        public String getStatus() { return status; }
    }

    public static class Transaction {
        private Long transactionId;
        private String type;
        private Double amount;
        private String status;

        public Transaction(Long transactionId, String type, Double amount, String status) {
            this.transactionId = transactionId;
            this.type = type;
            this.amount = amount;
            this.status = status;
        }

        public Long getTransactionId() { return transactionId; }
        public String getType() { return type; }
        public Double getAmount() { return amount; }
        public String getStatus() { return status; }
    }
}

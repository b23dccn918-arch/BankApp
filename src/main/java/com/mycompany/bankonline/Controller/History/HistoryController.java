package com.mycompany.bankonline.Controller.History;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.mycompany.bankonline.Database.Transaction.TransactionHandler;
import com.mycompany.bankonline.DisplayScene.toComplaint;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Model.Transaction;
import com.mycompany.bankonline.Session.Session;

public class HistoryController implements Initializable {

    @FXML
    private Button complaintButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button accountButton;

    @FXML
    private Button transferButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button withdrawButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button paymentButton;

    @FXML
    private Button depositButton;

    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private Button filterButton;
    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, Long> colId;
    @FXML
    private TableColumn<Transaction, String> colFrom;
    @FXML
    private TableColumn<Transaction, String> colTo;
    @FXML
    private TableColumn<Transaction, String> colType;
    @FXML
    private TableColumn<Transaction, Double> colAmount;
    @FXML
    private TableColumn<Transaction, String> colDescription;
    @FXML
    private TableColumn<Transaction, Timestamp> colDate;
    @FXML
    private TableColumn<Transaction, String> colStatus;

    private static final TransactionHandler transactionHandler = new TransactionHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Navigation button events
        homeButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.DashBoard(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        accountButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.UserInfo(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        transferButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.Transfer(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        historyButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.History(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        withdrawButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.WithDraw(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        paymentButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.Payment(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        depositButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.Deposit(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        logoutButton.setOnAction(e -> handleLogout());
        complaintButton.setOnAction(e ->{
        try{
            toComplaint.Complaint((Stage) complaintButton.getScene().getWindow());
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }});
        colId.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getTransactionId()));
        colFrom.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFromAccount()));
        colTo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getToAccount()));
        colType.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getType()));
        colAmount.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getAmount()));
        colDescription.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDescription()));
        colDate.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getCreatedAt()));
        colStatus.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus()));

        loadAllTransactions();

        filterButton.setOnAction(e -> filterTransactions());
    }

    private void loadAllTransactions() {
        int accountId = Session.getInstance().getAccountId();
        List<Transaction> transactions = transactionHandler.getTransactionsByAccountId(accountId);
        ObservableList<Transaction> data = FXCollections.observableArrayList(transactions);
        transactionTable.setItems(data);
    }

    private void filterTransactions() {
        LocalDate from = fromDate.getValue();
        LocalDate to = toDate.getValue();
        if (from == null || to == null) {
            showMessage("Notice", "Please select both start and end dates.");
            return;
        }
        int accountId = Session.getInstance().getAccountId();
        List<Transaction> filtered = transactionHandler.filterTransactionsByAccountId(from, to, accountId);
        transactionTable.setItems(FXCollections.observableArrayList(filtered));
    }

    private void showMessage(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void handleLogout() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");
        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                try {
                    // Clear current user session
                    Session.getInstance().clear();
                    Stage stage = (Stage) logoutButton.getScene().getWindow();
                    toSignIn.SignIn(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

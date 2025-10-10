package com.mycompany.bankonline.Controller.History;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.mycompany.bankonline.Database.Transaction.TransactionHandler;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Model.Transaction;
import com.mycompany.bankonline.Session.Session;



public class HistoryController implements Initializable{

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

    @FXML private DatePicker fromDate;
    @FXML private DatePicker toDate;
    @FXML private Button filterButton;
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

    // private ObservableList<TransactionView> transactions = FXCollections.observableArrayList();

    private static final TransactionHandler transactionHandler = new TransactionHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Gán sự kiện cho các nút
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

        // mapping cột -> thuộc tính trong model
        colId.setCellValueFactory(cell -> cell.getValue().transactionIdProperty().asObject());
        colFrom.setCellValueFactory(cell -> cell.getValue().fromAccountProperty());
        colTo.setCellValueFactory(cell -> cell.getValue().toAccountProperty());
        colType.setCellValueFactory(cell -> cell.getValue().typeProperty());
        colAmount.setCellValueFactory(cell -> cell.getValue().amountProperty().asObject());
        colDescription.setCellValueFactory(cell -> cell.getValue().descriptionProperty());
        colDate.setCellValueFactory(cell -> cell.getValue().createdAtProperty());
        colStatus.setCellValueFactory(cell -> cell.getValue().statusProperty());

        loadAllTransactions();
        // xử lý lọc theo ngày
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
        int accountId = Session.getInstance().getAccountId();
        List<Transaction> filtered = transactionHandler.filterTransactionsByAccountId(from, to,accountId);
        transactionTable.setItems(FXCollections.observableArrayList(filtered));
    }

    private void handleLogout() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn đăng xuất?");
        alert.showAndWait().ifPresent(response -> {
        if (response == javafx.scene.control.ButtonType.OK) {
            try {

                //them tinh nang xoa sessions hien tai thong tin user (authentication)
                Session.getInstance().clear();
                // Lấy stage hiện tại
                Stage stage = (Stage) logoutButton.getScene().getWindow();
                // Chuyển về trang đăng nhập
                toSignIn.SignIn(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    }
}

package com.mycompany.bankonline.Controller.History;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;

import com.mycompany.bankonline.MainApp.Main;

public class HistoryController {

    @FXML
    private Button homeButton;

    @FXML
    private Button accountButton;

    @FXML
    private Button transferButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button cardButton;

    @FXML
    private Button logoutButton;

    @FXML private DatePicker fromDate;
    @FXML private DatePicker toDate;
    @FXML private Button filterButton;
    @FXML private TableView<TransactionView> transactionTable;

    @FXML private TableColumn<TransactionView, Long> colId;
    @FXML private TableColumn<TransactionView, String> colFrom;
    @FXML private TableColumn<TransactionView, String> colTo;
    @FXML private TableColumn<TransactionView, String> colType;
    @FXML private TableColumn<TransactionView, Double> colAmount;
    @FXML private TableColumn<TransactionView, String> colDescription;
    @FXML private TableColumn<TransactionView, String> colDate;
    @FXML private TableColumn<TransactionView, String> colStatus;

    private ObservableList<TransactionView> transactions = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
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
        cardButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.UserInfo(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        logoutButton.setOnAction(e -> handleLogout());

        // mapping cột -> thuộc tính trong model
        colId.setCellValueFactory(data -> data.getValue().transactionIdProperty().asObject());
        colFrom.setCellValueFactory(data -> data.getValue().fromUserProperty());
        colTo.setCellValueFactory(data -> data.getValue().toUserProperty());
        colType.setCellValueFactory(data -> data.getValue().typeProperty());
        colAmount.setCellValueFactory(data -> data.getValue().amountProperty().asObject());
        colDescription.setCellValueFactory(data -> data.getValue().descriptionProperty());
        colDate.setCellValueFactory(data -> data.getValue().createdAtProperty());
        colStatus.setCellValueFactory(data -> data.getValue().statusProperty());

        // load mẫu
        transactions.add(new TransactionView(1L,"User A","User B","transfer",1000.0,"Chuyển tiền","2025-09-28","success"));
        transactions.add(new TransactionView(2L,"User A",null,"deposit",5000.0,"Nạp tiền","2025-09-25","success"));

        transactionTable.setItems(transactions);

        // xử lý lọc theo ngày
        filterButton.setOnAction(e -> filterTransactions());
    }

    private void filterTransactions() {
        LocalDate from = fromDate.getValue();
        LocalDate to = toDate.getValue();

    }

    private void handleLogout() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn đăng xuất?");
        alert.showAndWait();
    } 
}

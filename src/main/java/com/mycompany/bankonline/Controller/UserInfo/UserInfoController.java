package com.mycompany.bankonline.Controller.UserInfo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;

import com.mycompany.bankonline.MainApp.Main;

public class UserInfoController {

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

    @FXML private Label fullNameLabel;
    @FXML private Label phoneLabel;
    @FXML private Label citizenIdLabel;
    @FXML private Label jobLabel;
    @FXML private Label addressLabel;
    @FXML private Label accountTypeLabel;
    @FXML private Label balanceLabel;
    @FXML private Label statusLabel;

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
    }
    private void handleLogout() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn đăng xuất?");
        alert.showAndWait();
    } 

    public void setUserData(int userId) {
        // TODO: load dữ liệu thật từ DB
        String fullName = "Nguyen Van A";
        String phone = "0123456789";
        String citizenId = "123456789";
        String job = "Engineer";
        String address = "123 Nguyen Trai, Hanoi";
        String accountType = "checking";
        BigDecimal balance = new BigDecimal("15000000");
        String status = "active";

        fullNameLabel.setText("Full Name: " + fullName);
        phoneLabel.setText("Phone: " + phone);
        citizenIdLabel.setText("Citizen ID: " + citizenId);
        jobLabel.setText("Job: " + job);
        addressLabel.setText("Address: " + address);
        accountTypeLabel.setText("Account Type: " + accountType);
        balanceLabel.setText("Balance: " + balance + " VND");
        statusLabel.setText("Status: " + status);
    }
}

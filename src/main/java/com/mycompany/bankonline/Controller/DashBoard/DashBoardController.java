/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.bankonline.Controller.DashBoard;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.mycompany.bankonline.Database.Account.AccountHandler;
import com.mycompany.bankonline.Database.Transaction.TransactionHandler;
import com.mycompany.bankonline.Database.UserInfo.UserInfoHandler;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Model.User;
import com.mycompany.bankonline.Session.Session;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class DashBoardController implements Initializable {

    @FXML
    private VBox notificationBox;


    @FXML
    private Label helloField;

    @FXML
    private Button homeButton;

    @FXML
    private Button accountButton;

    @FXML
    private Button withdrawButton;

    @FXML
    private Button transferButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button paymentButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label accountNumberField;

    @FXML
    private Label balanceField;

    @FXML
    private Button depositButton;

    private final AccountHandler accountHandler = new AccountHandler();
    private final UserInfoHandler userInfoHandler = new UserInfoHandler();
    private final TransactionHandler transactionHandler = new TransactionHandler();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadNotifications(Session.getInstance().getAccountId());
        loadUserInfo(Session.getInstance().getUserId());
        accountNumberField.setText(formatAccountNumber(accountHandler.getAccountNumberByAccountId(Session.getInstance().getAccountId())));
        balanceField.setText(String.format("%,.0f VND", accountHandler.getBalanceByAccountId(Session.getInstance().getAccountId())));
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
    }


    private void loadUserInfo(int userId) {
        User user = userInfoHandler.getUserById(userId);
        if (user != null) {
            helloField.setText("Xin chào, " + user.getFullName()+"!");
        }
    }

    private void loadNotifications(int accountId) {
        notificationBox.getChildren().clear();

        List<String> messages = transactionHandler.getRecentTransactions(accountId, 5);

        if (messages.isEmpty()) {
            notificationBox.getChildren().add(new Label("• Không có thông báo mới."));
            return;
        }

        for (String msg : messages) {
            Label label = new Label(msg);
            label.setStyle("-fx-font-size: 14px; -fx-text-fill: #2E4053;");
            notificationBox.getChildren().add(label);
        }
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
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn đăng xuất?");
        alert.showAndWait().ifPresent(response -> {
        if (response == javafx.scene.control.ButtonType.OK) {
            try {
                
                //xoa thong tin trong session
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
    
    public String formatAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            return "";
        }
        StringBuilder formatted = new StringBuilder();
        int length = accountNumber.length();

        for (int i = 0; i < length; i++) {
            formatted.append(accountNumber.charAt(i));
            if ((i + 1) % 4 == 0 && (i + 1) != length) {
                formatted.append(".");
            }
        }   
        return formatted.toString();
    }   
    
}

package com.mycompany.bankonline.Controller.DashBoard;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.mycompany.bankonline.Database.Account.AccountHandler;
import com.mycompany.bankonline.Database.Transaction.TransactionHandler;
import com.mycompany.bankonline.Database.UserInfo.UserInfoHandler;

import com.mycompany.bankonline.DisplayScene.toComplaint;
import com.mycompany.bankonline.DisplayScene.toDashBoard;
import com.mycompany.bankonline.DisplayScene.toDeposit;
import com.mycompany.bankonline.DisplayScene.toHistory;
import com.mycompany.bankonline.DisplayScene.toPayment;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.DisplayScene.toTransfer;
import com.mycompany.bankonline.DisplayScene.toUserInfo;
import com.mycompany.bankonline.DisplayScene.toWithdraw;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Model.User;
import com.mycompany.bankonline.Session.Session;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private Button complaintButton;

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

    @FXML
    private Button toggleBalanceButton;
    private boolean isBalanceVisible = false;
    private double currentBalance = 0;

    private final AccountHandler accountHandler = new AccountHandler();
    private final UserInfoHandler userInfoHandler = new UserInfoHandler();
    private final TransactionHandler transactionHandler = new TransactionHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int accountId = Session.getInstance().getAccountId();
        Account currentAccount = accountHandler.findAccountByAccountId(accountId);

        currentBalance = currentAccount.getBalance();
        toggleBalanceButton.setOnAction(e -> {
            isBalanceVisible = !isBalanceVisible;
            if (isBalanceVisible) {
                balanceField.setText(String.format("%,.0f VND", currentBalance));
                toggleBalanceButton.setText("Hide");
            } else {
                balanceField.setText("•••••••• VND");
                toggleBalanceButton.setText("Show");
            }
        });

        loadNotifications(Session.getInstance().getAccountId());
        loadUserInfo(Session.getInstance().getUserId());
        accountNumberField.setText(formatAccountNumber(currentAccount.getAccountNumber()));
        
        // Gán sự kiện cho các nút
        homeButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toDashBoard.DashBoard(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        accountButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toUserInfo.UserInfo(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        transferButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toTransfer.Transfer(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        historyButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toHistory.History(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        withdrawButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toWithdraw.WithDraw(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        paymentButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toPayment.Payment(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        depositButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toDeposit.Deposit(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        complaintButton.setOnAction(e ->{
        try{
            toComplaint.Complaint((Stage) complaintButton.getScene().getWindow());
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }});
        logoutButton.setOnAction(e -> handleLogout());

    }

    private void loadUserInfo(int userId) {
        User user = userInfoHandler.getUserById(userId);
        if (user != null) {
            helloField.setText("Hello, " + user.getFullName() + "!");
        }
    }

    private void loadNotifications(int accountId) {
        notificationBox.getChildren().clear();
        int limitMessages = 5;
        List<String> messages = transactionHandler.getRecentTransactions(accountId, limitMessages);

        if (messages.isEmpty()) {
            notificationBox.getChildren().add(new Label("• No recent notifications."));
            return;
        }

        for (String msg : messages) {
            Label label = new Label("• " + msg);
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
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out of this account?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Clear current user session
                    Session.getInstance().clear();

                    // Return to sign-in page
                    Stage stage = (Stage) logoutButton.getScene().getWindow();
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

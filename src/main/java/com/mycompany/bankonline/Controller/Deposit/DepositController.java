package com.mycompany.bankonline.Controller.Deposit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.mycompany.bankonline.Controller.PinDialog.PinDialogController;
import com.mycompany.bankonline.Database.Account.AccountHandler;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Session.Session;

import javafx.fxml.Initializable;

public class DepositController implements Initializable {

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
    private Button logoutButton;

    @FXML
    private Button paymentButton;

    @FXML
    private Button depositButton;

    @FXML
    private TextField amountField;

    @FXML
    private Button depositButtonSubmit;

    @FXML
    private Label balanceField;

    public final AccountHandler accountHandler = new AccountHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        depositButtonSubmit.setOnAction(e -> handleDeposit());
        int accountId = Session.getInstance().getAccountId();
        Account currentAccount = accountHandler.findAccountByAccountId(accountId);
        balanceField.setText(String.format("%,d VND", currentAccount.getBalance()));

        // Gán sự kiện cho các nút điều hướng
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

    private void handleDeposit() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/PinDialog/PinDialog.fxml"));
            Parent root = loader.load();

            Stage pinStage = new Stage();
            pinStage.setTitle("Pin Confirmation");
            pinStage.setScene(new Scene(root));
            pinStage.setResizable(false);
            pinStage.initModality(Modality.APPLICATION_MODAL);
            pinStage.showAndWait();


            PinDialogController pinController = loader.getController();
            if (!pinController.isPinConfirmed()) {
                showMessage("Notification", "Transaction cancelled.");
                return;
            }

            int accountId = Session.getInstance().getAccountId();
            Account currentAccount = accountHandler.findAccountByAccountId(accountId);

            String enteredPin = pinController.getEnteredPin();
            String currentAccountPin = currentAccount.getPin();

            if (!enteredPin.equals(currentAccountPin)) {
                showMessage("Notification", "Pin is incorrect. Transaction cancelled.");
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Error","Cannot open PIN dialog.");
            return;
        }

        String amountText = amountField.getText().trim();

        if (amountText.isEmpty()) {
            showMessage("Notification", "Please enter an amount to deposit.");
            return;
        }

        long amount = Long.parseLong(amountText);
        if (amount <= 0) {
            showMessage("Notification", "Please enter a valid amount greater than zero");
            return;
        }

        int accountId = Session.getInstance().getAccountId();

        // Thực hiện gửi tiền
        boolean success = accountHandler.depositMoney(accountId, amount, Timestamp.valueOf(LocalDateTime.now()));

        if (success) {
            showMessage("Thành công", "Gửi tiền thành công!");
            updateBalanceUI();
            amountField.clear();
        } else {
            showMessage("Thất bại", "Không thể gửi tiền, vui lòng thử lại sau");
        }
    }

    private void updateBalanceUI() {
        int accountId = Session.getInstance().getAccountId();
        Account currentAccount = accountHandler.findAccountByAccountId(accountId);
        Long updatedBalance = currentAccount.getBalance();
        balanceField.setText(String.format("%,d VND", updatedBalance));
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
}

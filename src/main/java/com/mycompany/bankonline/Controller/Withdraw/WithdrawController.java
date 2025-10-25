package com.mycompany.bankonline.Controller.Withdraw;

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
import com.mycompany.bankonline.Session.Session;

import javafx.fxml.Initializable;

public class WithdrawController implements Initializable {

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
    private Button withdrawButtonSubmit;

    @FXML
    private Button depositButton;

    @FXML
    private TextField amountField;

    @FXML
    private Label balanceField;

    private final AccountHandler accountHandler = new AccountHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        balanceField.setText(String.format("%,.0f VND", accountHandler.getBalanceByAccountId(Session.getInstance().getAccountId())));
        withdrawButtonSubmit.setOnAction(e -> handleWithdraw());

        // Set navigation button events
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

    private void handleWithdraw() {
        try {
            // Open PIN confirmation dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/PinDialog/PinDialog.fxml"));
            Parent root = loader.load();

            Stage pinStage = new Stage();
            pinStage.setTitle("PIN Confirmation");
            pinStage.setScene(new Scene(root));
            pinStage.setResizable(false);
            pinStage.initModality(Modality.APPLICATION_MODAL); // Block other windows
            pinStage.showAndWait();

            // Get controller to check PIN result
            PinDialogController pinController = loader.getController();
            if (!pinController.isPinConfirmed()) {
                showMessage("Notice", "Transaction cancelled!");
                return;
            }

            String enteredPin = pinController.getEnteredPin();
            String currentAccountPin = accountHandler.getPinByAccountId(Session.getInstance().getAccountId());

            if (!enteredPin.equals(currentAccountPin)) {
                showMessage("Notice", "Incorrect PIN!");
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error opening PIN confirmation dialog.");
            return;
        }

        String amountText = amountField.getText().trim();

        // Validate input amount
        if (amountText.isEmpty()) {
            showMessage("Notice", "Please enter an amount!");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                showMessage("Notice", "Amount must be greater than 0!");
                return;
            }
        } catch (NumberFormatException e) {
            showMessage("Notice", "Invalid amount!");
            return;
        }

        int accountId = Session.getInstance().getAccountId();

        // Perform withdrawal in the database
        boolean success = accountHandler.withdrawMoney(accountId, amount, Timestamp.valueOf(LocalDateTime.now()));

        if (success) {
            showMessage("Notice", "Withdrawal successful!");
            updateBalanceUI();
            amountField.clear();
        } else {
            showMessage("Notice", "Withdrawal failed! Please try again.");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Transaction Error");
        alert.setHeaderText("Unable to complete transaction");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateBalanceUI() {
        double updatedBalance = accountHandler.getBalanceByAccountId(Session.getInstance().getAccountId());
        balanceField.setText(String.format("%,.0f VND", updatedBalance));
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
            if (response == ButtonType.OK) {
                try {
                    // Clear current session (authentication info)
                    Session.getInstance().clear();

                    // Get current stage and navigate back to Sign In
                    Stage stage = (Stage) logoutButton.getScene().getWindow();
                    toSignIn.SignIn(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

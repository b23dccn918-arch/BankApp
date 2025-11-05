/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.bankonline.Controller.Transfer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.mycompany.bankonline.Controller.PinDialog.PinDialogController;
import com.mycompany.bankonline.Database.Account.AccountHandler;
import com.mycompany.bankonline.Database.Transfer.TransferHandler;
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
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Model.User;
import com.mycompany.bankonline.Session.Session;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author vietnh
 */
public class TransferController implements Initializable {

    @FXML
    private TextField recipientAccountField;


    @FXML
    private Label recipientNameLabel;

    @FXML
    private TextField amountField;
    @FXML
    private TextField messageField;

    @FXML
    private Button complaintButton;

    @FXML
    private Label balanceField;

    @FXML
    private Button transferButtonSubmit;


    @FXML
    private Button homeButton;

    @FXML
    private Button accountButton;

    @FXML
    private Button paymentButton;

    @FXML
    private Button depositButton;

    @FXML
    private Button transferButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button cardButton;

    @FXML
    private Button withdrawButton;

    @FXML
    private Button logoutButton;

    private final TransferHandler transferHandler = new TransferHandler();
    private final UserInfoHandler userInfoHandler = new UserInfoHandler();
    private final AccountHandler accountHandler = new AccountHandler();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Account currentAccount = accountHandler.findAccountByAccountId(Session.getInstance().getAccountId());

        recipientAccountField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) { 
                String accountNumber = recipientAccountField.getText().trim();
                if (!accountNumber.isEmpty()) {
                    loadRecipientName(accountNumber);
                } else {
                    recipientNameLabel.setText("");
                }
            }
        });

        transferButtonSubmit.setOnAction(this::handleTransfer);
        
        balanceField.setText(String.format("%,d VND", currentAccount.getBalance()));
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
        complaintButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toComplaint.Complaint(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        logoutButton.setOnAction(e -> handleLogout());
    }

    private void handleTransfer(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/PinDialog/PinDialog.fxml"));
        Parent root = loader.load();

        Stage pinStage = new Stage();
        pinStage.setTitle("PIN Verification");
        pinStage.setScene(new Scene(root));
        pinStage.setResizable(false);
        pinStage.initModality(Modality.APPLICATION_MODAL); // Chặn các cửa sổ khác
        pinStage.showAndWait();

        PinDialogController pinController = loader.getController();
        if (!pinController.isPinConfirmed()) {
            showMessage("Notification", "Transaction canceled!");
            return;
        }

        Account currentAccount = accountHandler.findAccountByAccountId(Session.getInstance().getAccountId());

        String enteredPin = pinController.getEnteredPin();

        String currentAccountPin = currentAccount.getPin();
        if (!enteredPin.equals(currentAccountPin)) {
            showMessage("Notification", "Incorrect PIN!");
            return;
        }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error opening PIN verification dialog.");
            return;
        }

        String recipient = recipientAccountField.getText().trim();
        String amountText = amountField.getText().trim();
        String message = messageField.getText().trim();


        if (recipient.isEmpty() || amountText.isEmpty()) {
            showErrorAlert("Please fill in all required fields.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                showErrorAlert("Amount must be greater than 0.");
                return;
            }
            int senderAccountId = Session.getInstance().getAccountId();
            Account senderAccount = accountHandler.findAccountByAccountId(senderAccountId);
            if (senderAccount.getBalance() < amount) {
                showErrorAlert("Insufficient balance.");
                return;
            }

            Boolean result = transferHandler.transferMoney(senderAccountId, recipient, amount, message);

            if (result) {
                Account currentAccount = accountHandler.findAccountByAccountId(Session.getInstance().getAccountId());
                balanceField.setText(String.format("%,d VND", currentAccount.getBalance()));
                showSuccessAlert("Money transferred successfully!");
            } else {
                showErrorAlert("Transfer failed.");
            }

        } catch (NumberFormatException e) {
            showErrorAlert("Invalid amount format.");
        }
    }

    private void loadRecipientName(String accountNumber) {
        try {
            Account currentAccount = accountHandler.findAccountByAccountId(Session.getInstance().getAccountId());
            Account recipientAccount = accountHandler.findAccountByAccountNumber(accountNumber);
            User recipientUser = userInfoHandler.getUserById(
                    recipientAccount != null ? recipientAccount.getUserId() : -1);
            String recipientName = recipientUser != null ? recipientUser.getFullName() : "Unknown";
            String currentAccountNumber = currentAccount.getAccountNumber();
            if (accountNumber.equals(currentAccountNumber)) {
                recipientNameLabel.setText("You cannot transfer to your own account!");
                return;
            }
            if (recipientName != null) {
                recipientNameLabel.setText("Recipient: " + recipientName);
            } else {
                recipientNameLabel.setText("Account not found!");
            }
            FadeTransition fade = new FadeTransition(Duration.millis(400), recipientNameLabel);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();

        } catch (Exception e) {
            recipientNameLabel.setText("Error while fetching recipient info!");
            e.printStackTrace();
        }
    }


    private void showMessage(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Transaction Successful");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Transaction Error");
        alert.setHeaderText("Unable to complete the transaction");
        alert.setContentText(message);
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
    
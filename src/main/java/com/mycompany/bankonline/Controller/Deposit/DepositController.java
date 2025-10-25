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
        balanceField.setText(String.format("%,.0f VND", accountHandler.getBalanceByAccountId(Session.getInstance().getAccountId())));

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
            // Mở hộp thoại xác nhận PIN
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/PinDialog/PinDialog.fxml"));
            Parent root = loader.load();

            Stage pinStage = new Stage();
            pinStage.setTitle("Xác nhận mã PIN");
            pinStage.setScene(new Scene(root));
            pinStage.setResizable(false);
            pinStage.initModality(Modality.APPLICATION_MODAL);
            pinStage.showAndWait();

            // Kiểm tra kết quả nhập PIN
            PinDialogController pinController = loader.getController();
            if (!pinController.isPinConfirmed()) {
                showMessage("Thông báo", "Giao dịch đã bị hủy.");
                return;
            }

            String enteredPin = pinController.getEnteredPin();
            String currentAccountPin = accountHandler.getPinByAccountId(Session.getInstance().getAccountId());

            if (!enteredPin.equals(currentAccountPin)) {
                showMessage("Thông báo", "Mã PIN không chính xác. Vui lòng thử lại!");
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Không thể mở hộp thoại nhập mã PIN.");
            return;
        }

        String amountText = amountField.getText().trim();

        // Kiểm tra nhập liệu
        if (amountText.isEmpty()) {
            showMessage("Thông báo", "Vui lòng nhập số tiền cần gửi.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                showMessage("Thông báo", "Số tiền phải lớn hơn 0.");
                return;
            }
        } catch (NumberFormatException e) {
            showMessage("Thông báo", "Vui lòng nhập đúng định dạng số tiền.");
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
            showMessage("Thất bại", "Không thể gửi tiền. Vui lòng thử lại sau.");
        }
    }

    private void updateBalanceUI() {
        double updatedBalance = accountHandler.getBalanceByAccountId(Session.getInstance().getAccountId());
        balanceField.setText(String.format("%,.0f VND", updatedBalance));
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Lỗi hệ thống");
        alert.setHeaderText("Không thể thực hiện giao dịch");
        alert.setContentText(message);
        alert.showAndWait();
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

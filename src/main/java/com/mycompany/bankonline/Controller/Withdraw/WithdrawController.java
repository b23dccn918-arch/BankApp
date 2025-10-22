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
        withdrawButtonSubmit.setOnAction(e-> handleWithdraw());
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

    private void handleWithdraw() {

        try {
             // Mở hộp thoại nhập PIN
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/PinDialog/PinDialog.fxml"));
        Parent root = loader.load();

        Stage pinStage = new Stage();
        pinStage.setTitle("Xác nhận mã PIN");
        pinStage.setScene(new Scene(root));
        pinStage.setResizable(false);
        pinStage.initModality(Modality.APPLICATION_MODAL); // Chặn các cửa sổ khác
        pinStage.showAndWait();

        // Lấy controller để kiểm tra kết quả
        PinDialogController pinController = loader.getController();
        if (!pinController.isPinConfirmed()) {
            showMessage("Thông báo", "Giao dịch bị hủy!");
            return;
        }

        String enteredPin = pinController.getEnteredPin();

        String currentAccountPin = accountHandler.getPinByAccountId(Session.getInstance().getAccountId());
        if (!enteredPin.equals(currentAccountPin)) {
            showMessage("Thông báo", "Mã PIN không chính xác!");
            return;
        }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Lỗi khi mở hộp thoại nhập PIN.");
            return;
        }



        String amountText = amountField.getText().trim();
        
        // Kiểm tra nhập đúng số
        if (amountText.isEmpty()) {
            showMessage("Thông báo", "Vui lòng nhập số tiền!");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                showMessage("Thông báo", "Số tiền phải lớn hơn 0!");
                return;
            }
        } catch (NumberFormatException e) {
            showMessage("Thông báo", "Số tiền không hợp lệ!");
            return;
        }

        int accountId = Session.getInstance().getAccountId();

        // Thực hiện cập nhật vào database
        boolean success = accountHandler.withdrawMoney(accountId, amount, Timestamp.valueOf(LocalDateTime.now()));

        if (success) {
            showMessage("Thông báo", "Rut tiền thành công!");
            updateBalanceUI();
            amountField.clear();
        } else {
            showMessage("Thông báo", "Rut tiền thất bại! Vui lòng thử lại.");
        }
    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi giao dịch");
        alert.setHeaderText("Không thể thực hiện giao dịch");
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

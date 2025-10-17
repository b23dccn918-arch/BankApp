/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.bankonline.Controller.Transfer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.mycompany.bankonline.Database.Account.AccountHandler;
import com.mycompany.bankonline.Database.Transfer.TransferHandler;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Session.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author vietnh
 */
public class TransferController implements Initializable {

    @FXML
    private TextField recipientAccountField;
    @FXML
    private TextField amountField;
    @FXML
    private TextField messageField;

    @FXML
    private Label statusFailedLabel;
    
    @FXML
    private Label statusSuccessLabel;

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

    private final AccountHandler accountHandler = new AccountHandler();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        transferButtonSubmit.setOnAction(this::handleTransfer);
        
        // Gán sự kiện cho các nút
        balanceField.setText(String.format("%,.0f VND", accountHandler.getBalanceByAccountId(Session.getInstance().getAccountId())));
        statusFailedLabel.setVisible(false);
        statusSuccessLabel.setVisible(false);
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

    private void handleTransfer(ActionEvent event) {
        String recipient = recipientAccountField.getText().trim();
        String amountText = amountField.getText().trim();
        String message = messageField.getText().trim();

        // Reset label mỗi lần nhấn
        statusFailedLabel.setVisible(false);
        statusSuccessLabel.setVisible(false);

        if (recipient.isEmpty() || amountText.isEmpty()) {
            showErrorAlert("Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                showErrorAlert("Số tiền phải lớn hơn 0.");
                return;
            }
            int senderAccountId = Session.getInstance().getAccountId();
            String result = transferHandler.transferMoney(senderAccountId, recipient, amount, message);

            if (result.equals("Success")) {
                balanceField.setText(String.format("%,.0f VND", accountHandler.getBalanceByAccountId(Session.getInstance().getAccountId())));
                showSuccessAlert("Chuyển tiền thành công!");
            } else {
                showErrorAlert(result);
            }

        } catch (NumberFormatException e) {
            showErrorAlert("Số tiền không hợp lệ.");
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
        alert.setTitle("Thành công");
        alert.setHeaderText("Giao dịch thành công");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi giao dịch");
        alert.setHeaderText("Không thể thực hiện giao dịch");
        alert.setContentText(message);
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
    
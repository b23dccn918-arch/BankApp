package com.mycompany.bankonline.Controller.ResetPassword;

import java.sql.Connection;

import com.mycompany.bankonline.Database.ForgotPassword.ForgotPasswordHandler;
import com.mycompany.bankonline.DisplayScene.toSignIn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ResetPasswordController {
    @FXML private Label emailLabel;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label messageLabel;

    @FXML
    private Button BackToLoginButton;

    private String email;
    private String token;
    private ForgotPasswordHandler forgotPasswordHandler = new ForgotPasswordHandler();


    public void setEmailAndToken(String email, String token) {
        this.email = email;
        this.token = token;
        emailLabel.setText("Email: " + email);
    }

    @FXML
    private void handleResetPassword() {
        String newPass = newPasswordField.getText();
        String confirm = confirmPasswordField.getText();

        if (newPass.isEmpty() || confirm.isEmpty()) {
            showMessage("Thông báo", "Vui lòng nhập đầy đủ thông tin.");
            return;
        }
        if (!newPass.equals(confirm)) {
            showMessage("Thông báo", "Mật khẩu xác nhận không khớp.");
            return;
        }

        boolean success = forgotPasswordHandler.resetPassword(email, token, newPass);
        if (success) {
            showMessage("Notification", "Cập nhật mật khẩu thành công!");
            try {
                Stage stage = (Stage) BackToLoginButton.getScene().getWindow();
                toSignIn.SignIn(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showMessage("Error", "Lỗi khi cập nhật mật khẩu.");
        }
    }

    @FXML
    private void handleBackToLogin(ActionEvent event) {
        try {
            Stage stage = (Stage) BackToLoginButton.getScene().getWindow();
            toSignIn.SignIn(stage);
        } catch (Exception e) {
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
}

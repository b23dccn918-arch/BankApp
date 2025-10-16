package com.mycompany.bankonline.Controller.ResetPassword;

import java.sql.Connection;

import com.mycompany.bankonline.Database.ForgotPassword.ForgotPasswordHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ResetPasswordController {
    @FXML private Label emailLabel;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label messageLabel;

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
            messageLabel.setText("Vui lòng nhập đầy đủ thông tin.");
            return;
        }
        if (!newPass.equals(confirm)) {
            messageLabel.setText("Mật khẩu xác nhận không khớp.");
            return;
        }

        boolean success = forgotPasswordHandler.resetPassword(email, token, newPass);
        if (success) {
            messageLabel.setText("Cập nhật mật khẩu thành công!");
        } else {
            messageLabel.setText("Lỗi khi cập nhật mật khẩu.");
        }
    }
}

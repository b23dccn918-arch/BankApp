package com.mycompany.bankonline.Controller.ChangePassword;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ChangePasswordController {
    @FXML
    private TextField phoneNumberField;

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleChangePassword(ActionEvent event) {
        String phoneNumber = phoneNumberField.getText();
        String current = currentPasswordField.getText();
        String newPass = newPasswordField.getText();
        String confirm = confirmPasswordField.getText();

        if (phoneNumber.isEmpty() || current.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
            showMessage("Thông báo", "Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        if (!newPass.equals(confirm)) {
            showMessage("Thông báo", "Mật khẩu xác nhận không khớp.");
            return;
        }

        // 🔹 TODO: Gọi service kiểm tra và cập nhật mật khẩu trong DB
        boolean success = changePasswordInDatabase(phoneNumber, current, newPass);

        if (success) {
            showMessage("Thông báo","Mật khẩu được thay đổi thành công" );
        } else {
            showMessage("Thông báo", "Số điện thoại hoặc mật khẩu bị sai");
        }
    }

    private boolean changePasswordInDatabase(String email, String current, String newPass) {
        // 🔹 Ví dụ logic kiểm tra (tạm thời)
        // Sau này bạn có thể dùng UserService để kiểm tra DB
        return email.equalsIgnoreCase("demo@bank.com") && current.equals("123456");
    }

    private void showMessage(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleBackToSignIn(ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/SignIn/SignIn.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) phoneNumberField.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

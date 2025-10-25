package com.mycompany.bankonline.Controller.ChangePassword;

import com.mycompany.bankonline.Database.ChangePassword.ChangePasswordHandler;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.MainApp.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ChangePasswordController {
    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button btnChangePassword;

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    private final ChangePasswordHandler changePasswordHandler = new ChangePasswordHandler();

    @FXML
    private void handleChangePassword(ActionEvent event) {
        String phoneNumber = phoneNumberField.getText();
        String current = currentPasswordField.getText();
        String newPass = newPasswordField.getText();
        String confirm = confirmPasswordField.getText();

        if (phoneNumber.isEmpty() || current.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
            showMessage("Notification", "Please fill in all required fields.");
            return;
        }

        if (!newPass.equals(confirm)) {
            showMessage("Notification", "Confirmation password does not match.");
            return;
        }

        boolean check = changePasswordHandler.checkCurrentPassword(phoneNumber, current);
        if (!check) {
            showMessage("Notification", "Phone number or current password is incorrect.");
            return;
        }

        boolean success = changePasswordHandler.changePasswordInDatabase(phoneNumber, current, newPass);

        if (success) {
            showMessage("Notification", "Password changed successfully.");
        } else {
            showMessage("Notification", "Phone number or current password is incorrect.");
        }
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
            Stage stage = (Stage) phoneNumberField.getScene().getWindow();
            toSignIn.SignIn(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

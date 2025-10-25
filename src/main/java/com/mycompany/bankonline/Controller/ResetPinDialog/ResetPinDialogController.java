package com.mycompany.bankonline.Controller.ResetPinDialog;

import com.mycompany.bankonline.Database.Account.AccountHandler;
import com.mycompany.bankonline.Database.ForgotPin.ForgotPinHandler;
import com.mycompany.bankonline.Database.UserInfo.UserInfoHandler;
import com.mycompany.bankonline.Session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ResetPinDialogController {

    @FXML
    private PasswordField newPinField;
    @FXML
    private PasswordField confirmPinField;
    @FXML
    private Label errorLabel;

    private final AccountHandler accountHandler = new AccountHandler();
    private final ForgotPinHandler forgotPinHandler = new ForgotPinHandler();
    private final UserInfoHandler userInfoHandler = new UserInfoHandler();
    private String token;

    @FXML
    private Button saveButton, cancelButton;

    public void setToken(String token) {
        this.token = token;
    }

    @FXML
    public void initialize() {
        saveButton.setOnAction(e -> handleSave());
        cancelButton.setOnAction(e -> ((Stage) cancelButton.getScene().getWindow()).close());
    }

    private void handleSave() {
        String newPin = newPinField.getText().trim();
        String confirmPin = confirmPinField.getText().trim();

        if (!newPin.matches("\\d{6}")) {
            errorLabel.setText("PIN must be exactly 6 digits!");
            return;
        }
        if (!newPin.equals(confirmPin)) {
            errorLabel.setText("PIN codes do not match!");
            return;
        }

        String email = userInfoHandler.getEmailByUserId(Session.getInstance().getUserId());

        boolean updated = forgotPinHandler.resetPin(email, token, newPin);
        if (updated) {
            showMessage("Success", "Your PIN has been successfully reset!");
            ((Stage) saveButton.getScene().getWindow()).close();
        } else {
            showMessage("Error", "Failed to update your PIN. Please try again.");
        }
    }

    private void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

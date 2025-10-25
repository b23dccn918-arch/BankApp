package com.mycompany.bankonline.Controller.PinDialog;

import java.io.IOException;

import com.mycompany.bankonline.Database.ForgotPin.ForgotPinHandler;
import com.mycompany.bankonline.Service.EmailService;
import com.mycompany.bankonline.Session.Session;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PinDialogController {

    @FXML 
    private PasswordField pinField;
    @FXML 
    private Button confirmButton;
    @FXML 
    private Button cancelButton;
    @FXML 
    private Label errorLabel;
    @FXML
    private Hyperlink forgotPinLink;

    private boolean pinConfirmed = false;
    private String enteredPin;
    private final ForgotPinHandler forgotPinHandler = new ForgotPinHandler();

    @FXML
    private void initialize() {
        forgotPinLink.setOnAction(event -> handleForgotPin());
        confirmButton.setOnAction(e -> handleConfirm());
        cancelButton.setOnAction(e -> handleCancel());
    }

    private void handleForgotPin() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Forgot PIN");
        alert.setHeaderText(null);
        alert.setContentText("A 6-digit verification code will be sent to your registered email.");

        // Wait for user to press OK
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                int accountId = Session.getInstance().getAccountId();
                boolean sent = forgotPinHandler.sendResetToken(accountId);
                if (sent) {
                    openTokenConfirmDialog();
                }
            }
        });
    }

    private void openTokenConfirmDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/TokenConfirmDialog/TokenConfirmDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Verify Your Code");
            dialogStage.setScene(new Scene(root));
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleConfirm() {
        String pin = pinField.getText().trim();

        if (!pin.matches("\\d{6}")) {
            errorLabel.setText("PIN must be exactly 6 digits!");
            return;
        }

        this.enteredPin = pin;
        this.pinConfirmed = true;
        ((Stage) pinField.getScene().getWindow()).close();
    }

    private void handleCancel() {
        ((Stage) pinField.getScene().getWindow()).close();
    }

    public boolean isPinConfirmed() {
        return pinConfirmed;
    }

    public String getEnteredPin() {
        return enteredPin;
    }
}

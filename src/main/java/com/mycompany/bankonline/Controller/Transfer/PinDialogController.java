package com.mycompany.bankonline.Controller.Transfer;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PinDialogController {

    @FXML private PasswordField pinField;
    @FXML private Button confirmButton;
    @FXML private Button cancelButton;
    @FXML private Label errorLabel;

    private boolean pinConfirmed = false;
    private String enteredPin;

    @FXML
    private void initialize() {
        confirmButton.setOnAction(e -> handleConfirm());
        cancelButton.setOnAction(e -> handleCancel());
    }

    private void handleConfirm() {
        String pin = pinField.getText().trim();

        if (!pin.matches("\\d{6}")) {
            errorLabel.setText("PIN phải gồm 6 chữ số!");
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

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

    public void setToken(String token) {
        this.token = token;
    }

    @FXML
    public void initialize() {
        // Sự kiện nút lưu
        saveButton.setOnAction(e -> handleSave());
        cancelButton.setOnAction(e -> ((Stage) cancelButton.getScene().getWindow()).close());
    }

    @FXML
    private Button saveButton, cancelButton;

    private void handleSave() {
        String newPin = newPinField.getText().trim();
        String confirmPin = confirmPinField.getText().trim();

        if (!newPin.matches("\\d{6}")) {
            errorLabel.setText("Mã PIN phải gồm 6 chữ số!");
            return;
        }
        if (!newPin.equals(confirmPin)) {
            errorLabel.setText("Hai mã PIN không trùng khớp!");
            return;
        }

        String email = userInfoHandler.getEmailByUserId(Session.getInstance().getUserId());

        boolean updated = forgotPinHandler.resetPin(email, token,newPin);
        if (updated) {
            showMessage("Thành công", "Mã PIN của bạn đã được đặt lại!");
            ((Stage) saveButton.getScene().getWindow()).close();
        } else {
            showMessage("Lỗi","Mã pin của bạn chưa được cập nhật thành công");
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

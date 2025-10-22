package com.mycompany.bankonline.Controller.TokenConfirmDialog;


import java.io.IOException;

import com.mycompany.bankonline.Controller.ResetPassword.ResetPasswordController;
import com.mycompany.bankonline.Controller.ResetPinDialog.ResetPinDialogController;
import com.mycompany.bankonline.Database.ForgotPin.ForgotPinHandler;
import com.mycompany.bankonline.Database.UserInfo.UserInfoHandler;
import com.mycompany.bankonline.Model.User;
import com.mycompany.bankonline.Session.Session;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TokenConfirmDialogController {

    @FXML
    private TextField tokenField;
    @FXML
    private Label emailLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label errorLabel;

    @FXML
    private Label timerLabel;


    private int timeSeconds = 300; 
    private Timeline timeline;
    private final ForgotPinHandler forgotPinHandler = new ForgotPinHandler();
    private final UserInfoHandler userInfoHandler = new UserInfoHandler();
    private boolean confirmed = false;
    private String enteredToken;

    @FXML
    public void initialize() {
        
        startTimer();
        confirmButton.setOnAction(e -> handleConfirm());
        cancelButton.setOnAction(e -> handleCancel());
    }

    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeSeconds--;
            int minutes = timeSeconds / 60;
            int seconds = timeSeconds % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds));

            // Hết thời gian
            if (timeSeconds <= 0) {
                timeline.stop();
                timerLabel.setText("Mã đã hết hạn!");
                confirmButton.setDisable(true);
                tokenField.setDisable(true);
            }
        }));
        timeline.setCycleCount(timeSeconds);
        timeline.play();
    }

    private void handleConfirm() {
        String token = tokenField.getText().trim();

        if (token.isEmpty() || !token.matches("\\d{6}")) {
            showMessage("Thông báo", "Mã xác thực phải gồm 6 chữ số!");
            return;
        }
        String email = userInfoHandler.getEmailByUserId(Session.getInstance().getUserId());
        boolean isValid = forgotPinHandler.verifyToken(email,token);
        if (!isValid) {
            showMessage("Thông báo", "Mã xác thực không hợp lệ hoặc đã hết hạn.");
            return;
        }

        Stage currentStage = (Stage) confirmButton.getScene().getWindow();
        currentStage.close();

        openResetPinDialog(email,token);
    }

    private void openResetPinDialog(String email,String token) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/ResetPinDialog/ResetPinDialog.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Đặt lại mã PIN");
            ResetPinDialogController controller = loader.getController();
            controller.setToken(token);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
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

    private void handleCancel() {
        confirmed = false;
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getEnteredToken() {
        return enteredToken;
    }

    public void setEmail(String email) {
        emailLabel.setText("Mã xác thực đã được gửi đến: " + email);
    }
}
package com.mycompany.bankonline.Controller.ConfirmToken;

import java.sql.Connection;

import com.mycompany.bankonline.Controller.ResetPassword.ResetPasswordController;
import com.mycompany.bankonline.Database.ForgotPassword.ForgotPasswordHandler;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.MainApp.Main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ConfirmTokenController {
    @FXML private Label emailLabel;
    @FXML private TextField tokenField;
    @FXML private Label messageLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private Button BackToLoginButton;

    @FXML
    private Label timerLabel;

    private String email;
    private ForgotPasswordHandler forgotPasswordHandler = new ForgotPasswordHandler();

    public void setEmail(String email) {
        this.email = email;
        emailLabel.setText("Email: " + email);
    }
    private int timeSeconds = 300; 
    private Timeline timeline;

    @FXML
    public void initialize() {
        startTimer();
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

    @FXML
    private void handleVerify() {
        String token = tokenField.getText().trim();
        if (token.isEmpty()) {
            messageLabel.setText("Vui lòng nhập mã xác thực.");
            return;
        }

        boolean valid = forgotPasswordHandler.verifyToken(email, token);
        if (valid) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/ResetPassword/ResetPassword.fxml"));
                Stage stage = (Stage) tokenField.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/ResetPassword/ResetPassword.css").toExternalForm());
                ResetPasswordController ctrl = loader.getController();
                ctrl.setEmailAndToken(email, token);
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showMessage("Notification", "Mã xác thực không hợp lệ hoặc đã hết hạn.");
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

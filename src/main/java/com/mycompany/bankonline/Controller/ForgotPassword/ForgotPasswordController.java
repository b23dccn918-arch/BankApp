package com.mycompany.bankonline.Controller.ForgotPassword;

import com.mycompany.bankonline.Controller.ConfirmToken.ConfirmTokenController;
import com.mycompany.bankonline.Database.ForgotPassword.ForgotPasswordHandler;
import com.mycompany.bankonline.Database.UserInfo.UserInfoHandler;
import com.mycompany.bankonline.DisplayScene.toConfirmToken;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Service.GenerateToken;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ForgotPasswordController {
    @FXML
    private Label StatusLabel;

    @FXML
    private Button BackToLoginButton;

    @FXML
    private TextField emailField;

    @FXML
    private Label messageLabel;

    private final UserInfoHandler userInfoHandler = new UserInfoHandler();
    private final GenerateToken generateToken = new GenerateToken();
    private final ForgotPasswordHandler forgotPasswordHandler = new ForgotPasswordHandler();

    @FXML
    private void handleSendRecoveryCode(ActionEvent event) {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            showMessage("Notice", "Please enter your email address!");
            return;
        }
        
        if (!userInfoHandler.isEmailExists(email)) {
            showMessage("Notice", "This email address does not exist in our system!");
            return;
        }

        boolean sent = forgotPasswordHandler.sendResetToken(email);
        if (sent) {
            showMessage("Notification", "A verification code has been sent to your email.");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/ConfirmToken/ConfirmToken.fxml"));
                Stage stage = (Stage) emailField.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/ConfirmToken/ConfirmToken.css").toExternalForm());
                ConfirmTokenController ctrl = loader.getController();
                ctrl.setEmail(email);
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showMessage("Notification", "Failed to send verification code. Please try again later.");
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
    private void handleBackToLogin(ActionEvent event) {
        try {
            Stage stage = (Stage) BackToLoginButton.getScene().getWindow();
            toSignIn.SignIn(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.mycompany.bankonline.Controller.SignIn;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mycompany.bankonline.Database.SignIn.SignInHandler;
import com.mycompany.bankonline.DisplayScene.*;
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Session.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ControllerSignIn {
	@FXML
	private TextField Username;
	@FXML
	private PasswordField Password;
	@FXML
    private Button SignInButton;
	@FXML
	private Button SignUpButton;
	@FXML
	private static Alert alert = new Alert(Alert.AlertType.INFORMATION);
	
	@FXML
    public void initialize() {
        SignInButton.disableProperty().bind(
                Username.textProperty().isEmpty()
                        .or(Password.textProperty().isEmpty())
        );
    }
	
	public void handleSignInButton(ActionEvent event) throws IOException {
		String username = Username.getText();
		String password = Password.getText();
		Button clickedButton = (Button) event.getSource();
		Scene currentScene = clickedButton.getScene();
		Stage currentStage = (Stage) currentScene.getWindow();	
		if(SignInHandler.checkSignIn(username, password) == true) {
			toDashBoard.DashBoard(currentStage);
		}
		else {
			Alert signInFailed = new Alert(Alert.AlertType.ERROR);
			signInFailed.setTitle("Sign In Failed");
			signInFailed.setContentText("Username or password is incorrect !");
			signInFailed.show();
		}
	} 

	
	  public void handleSignUpButton(ActionEvent event) {
	        // Lấy stage hiện tại từ nút được nhấn
		  	Button clickedButton = (Button) event.getSource();
		  	Scene currentScene = clickedButton.getScene();
		  	Stage currentStage = (Stage) currentScene.getWindow();
	        try {
	            toSignUp.SignUp(currentStage); // Gọi phương thức SignUp từ Main class
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
}

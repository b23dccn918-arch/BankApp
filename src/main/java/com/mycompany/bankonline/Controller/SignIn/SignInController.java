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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

public class SignInController {
	@FXML
	private TextField Username;
	@FXML
	private PasswordField Password;

	@FXML
	private TextField PasswordVisible;
	@FXML
	private Button togglePasswordVisibility;

	@FXML
    private Button SignInButton;
	@FXML
	private Button SignUpButton;

	@FXML
	private Hyperlink forgotPassword;

	@FXML
	private Hyperlink changePassword;

	@FXML
	private static Alert alert = new Alert(Alert.AlertType.INFORMATION);
	
	@FXML
	private Hyperlink AdminSignIn;
	
	private boolean isPasswordVisible = false;

	@FXML
    public void initialize() {

		PasswordVisible.textProperty().bindBidirectional(Password.textProperty());

		forgotPassword.setOnAction(event->{
			Stage stage = (Stage) forgotPassword.getScene().getWindow();
			try {
				toForgotPassword.ForgotPassword(stage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		changePassword.setOnAction(event->{
			Stage stage = (Stage) changePassword.getScene().getWindow();
			try {
				toChangePassword.ChangePassword(stage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		// Chỉ cho phép nhập số
//	    Username.setTextFormatter(new TextFormatter<>(change -> {
//	        if (change.getControlNewText().matches("\\d*")) {
//	            return change; // chấp nhận nếu chỉ toàn số
//	        }
//	        return null; // bỏ qua nếu có ký tự khác
//	    }));

	    // ... phần còn lại trong initialize() của m

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
		else if(SignInHandler.checkSignIn(username, password) == false){
			Alert signInFailed = new Alert(Alert.AlertType.ERROR);
			signInFailed.setTitle("Sign In Failed");
			signInFailed.setContentText("Username or password is incorrect !");
			signInFailed.show();
			return;
		}
		
		else if(!SignInHandler.checkStatus(username, password)) {
			Alert signInFailed = new Alert(Alert.AlertType.ERROR);
			signInFailed.setTitle("Sign In Failed");
			signInFailed.setContentText("Your account is currently locked. Please contact support for assistance !");
			signInFailed.show();
			return;
		}
		
	} 

	@FXML
	private void handleTogglePasswordVisibility() {
		isPasswordVisible = !isPasswordVisible;
		if (isPasswordVisible) {
			PasswordVisible.setVisible(true);
			PasswordVisible.setManaged(true);
			Password.setVisible(false);
			Password.setManaged(false);
			togglePasswordVisibility.setText("Hide"); 
		} else {
			PasswordVisible.setVisible(false);
			PasswordVisible.setManaged(false);
			Password.setVisible(true);
			Password.setManaged(true);
			togglePasswordVisibility.setText("Show");
		}
	}

	
	  public void handleSignUpButton(ActionEvent event) {
		  	Button clickedButton = (Button) event.getSource();
		  	Scene currentScene = clickedButton.getScene();
		  	Stage currentStage = (Stage) currentScene.getWindow();
	        try {
	            toSignUp.SignUp(currentStage); 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	  
	  @FXML
	  private void handleAdminSignIn(ActionEvent event) {
	      try {
	          FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/Admin/SignIn/AdminSignIn.fxml"));
	          Parent root = loader.load();
	          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	          Scene scene = new Scene(root);
	          scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/Admin/SignIn/StyleAdminSignIn.css").toExternalForm());
	          stage.setScene(scene);
	          stage.setTitle("Admin Sign In");
	          stage.show();
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	  }

}
	

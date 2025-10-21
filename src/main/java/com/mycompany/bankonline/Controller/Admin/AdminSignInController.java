package com.mycompany.bankonline.Controller.Admin;

import java.io.IOException;

import com.mycompany.bankonline.Database.Admin.AdminSignIn;
import com.mycompany.bankonline.Database.SignIn.SignInHandler;
import com.mycompany.bankonline.DisplayScene.toAdminDashBoard;
import com.mycompany.bankonline.DisplayScene.toDashBoard;
import com.mycompany.bankonline.MainApp.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminSignInController {
	
	@FXML
	private TextField Username, Password;
	
	@FXML
	private Button SignInButton;
	
	public void handleSignInButton(ActionEvent event) throws IOException {
		String username = Username.getText();
		String password = Password.getText();
		Button clickedButton = (Button) event.getSource();
		Scene currentScene = clickedButton.getScene();
		Stage currentStage = (Stage) currentScene.getWindow();	
		if(AdminSignIn.checkSignIn(username, password) == true) {
			toAdminDashBoard.Account(currentStage);
		}
		else {
			Alert signInFailed = new Alert(Alert.AlertType.ERROR);
			signInFailed.setTitle("Sign In Failed");
			signInFailed.setContentText("Username or password is incorrect !");
			signInFailed.show();
		}
	} 
	
	public void handleCustomerSignIn(ActionEvent event) {
		try {
	          FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/SignIn/SignInInterface.fxml"));
	          Parent root = loader.load();
	          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	          Scene scene = new Scene(root);
	          scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/SignIn/StyleSignIn.css").toExternalForm());
	          stage.setScene(scene);
	          stage.setTitle("Customer Sign In");
	          stage.show();
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	}
	
	

}

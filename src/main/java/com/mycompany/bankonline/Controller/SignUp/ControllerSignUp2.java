package com.mycompany.bankonline.Controller.SignUp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.DisplayScene.toSignUp;

import com.mycompany.bankonline.MainApp.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerSignUp2 {

	@FXML
	private Button BackButton2, FinishButton;

	@FXML
	private PasswordField Password, PinCode, ConfirmPass;

	@FXML
	private TextField PhoneNumber, PaymentAccount;
	private String identification, fullName, job, email, address, birth, gender;


	public void BackToSignUp_1(ActionEvent event) throws IOException {
	
		Button clickedButton = (Button) event.getSource();
		Scene currentScene = clickedButton.getScene();
		Stage currentStage = (Stage) currentScene.getWindow();
		toSignUp.SignUp(currentStage);
	}

	public void setUserData() {
		
	}
	

	public void getFinishButton(ActionEvent event) throws Exception {
				

	}
	

}
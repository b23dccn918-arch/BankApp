package com.mycompany.bankonline.Controller.SignUp;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.DisplayScene.toSignUp;
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Model.User;

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

public class ControllerSignUp {
	
	Account newAcc = new Account();
	User newCustomer = new User();

	@FXML
	private Button BackButton, NextButton, BackButton2, FinishButton;
	@FXML
	private TextField Identification, FullName, Job, Email, Address, Birth, Gender;

	
	private String phoneNumber, citizenIdentification, job, address, referrer;

	public void BackToSignIn(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
		Scene currentScene = clickedButton.getScene();
		Stage currentStage = (Stage) currentScene.getWindow();
		try {
			toSignIn.SignIn(currentStage); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getNextButton(ActionEvent event) throws Exception {
	    String identification = Identification.getText().trim();
	    String fullName = FullName.getText().trim();
	    String job = Job.getText().trim();
	    String gender = Gender.getText().trim();
	    String birth = Birth.getText().trim();
	    String mail = Email.getText().trim();
	    String address = Address.getText().trim();

	    if(true) {
	    	   if(identification == "" || fullName == "" || job == "" || gender == "" || birth == "" || mail == "" || address == "") {
	    	   Alert empty = new Alert(Alert.AlertType.ERROR);
	    	   empty.setHeaderText("Failed");
	    	   empty.setContentText("Please fill in all the information");
	    	   empty.show();
	       }
	       
	       else {
	    	    System.out.println("Qua buoc 1");
	    	    System.out.println(identification + " " + fullName + " " + job + " " + gender + " " + birth + " " + mail + " " + address);
	    	    Button clickedButton = (Button) event.getSource();
	    	    Scene currentScene = clickedButton.getScene();	
	    	    Stage currentStage = (Stage) currentScene.getWindow();
	    	    try {	
	    	    	toSignUp.SignUp2(currentStage); 
	    	    } catch (Exception e) {
	    	    	e.printStackTrace();
	    	    }
	       }

	    } 
	}


	

}
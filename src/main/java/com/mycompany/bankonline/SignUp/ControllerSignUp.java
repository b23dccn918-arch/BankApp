package com.mycompany.bankonline.SignUp;

import com.mycompany.bankonline.MainApp.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.mycompany.bankonline.SignIn.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerSignUp {
	
	Account newAcc = new Account();
	Customer newCustomer = new Customer();

	@FXML
	private Button BackButton, NextButton;
	@FXML
	private TextField PhoneNumber, Identification, Job, ReferralCode, Address, PinID;
	@FXML
	private static Alert alert = new Alert(Alert.AlertType.INFORMATION);
	
	private String username, password, accountNumber, fullName, birth, phoneNumber, citizenIdentification, gender, job, address, referrer, pin;
	private long balance;

	public void BackToSignIn(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
		Scene currentScene = clickedButton.getScene();
		Stage currentStage = (Stage) currentScene.getWindow();
		try {
			Main.SignIn(currentStage); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void getNextButton(ActionEvent event) throws Exception {
		String phonenum = PhoneNumber.getText();
		String number = Identification.getText();
		if(checkPhoneNumber(phonenum) && checkIdentification(number)) {
			System.out.println("dang ki thanh cong");
			citizenIdentification = number;
			phoneNumber = phonenum;
			job = Job.getText();
			referrer = ReferralCode.getText();
			address = Address.getText();
			//sang phan dang ki 2
			Button clickedButton = (Button) event.getSource();
			Scene currentScene = clickedButton.getScene();
			Stage currentStage = (Stage) currentScene.getWindow();
			Main.SignUp2(currentStage);
		}
		else {
			alert.setHeaderText("Failed");
	        alert.setContentText("Phone number or Citizen identification number already registered!");
	        alert.show();
		}


	}

	private static boolean checkPhoneNumber(String phonenum) throws Exception {
		String query = "SELECT * FROM Accounts";
		Connection con = Main.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		List<String> PhoneNumbers = new LinkedList<String>();
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			PhoneNumbers.add(rs.getString(1));
		}
		boolean exists = PhoneNumbers.stream().anyMatch(u -> u.equalsIgnoreCase(phonenum)); 
		return !exists;
	}
	
	private static boolean checkIdentification(String number) throws Exception {
		String query = "SELECT * FROM Customers";
		Connection con = Main.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		List<String> Identifications = new LinkedList<String>();
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Identifications.add(rs.getString(3));
		}
		boolean exists = Identifications.stream().anyMatch(u -> u.equalsIgnoreCase(number)); 
		return !exists;
	}

}

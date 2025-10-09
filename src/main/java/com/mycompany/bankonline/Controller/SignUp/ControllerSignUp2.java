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

	public void setUserData(String Identification, String FullName, String Job, String Gender, String Birth, String Email, String Address) {
		this.identification = Identification;
		this.fullName = FullName;
		this.job = Job;
		this.gender = Gender;
		this.birth = Birth;
		this.email = Email;
		this.address = Address;

	}
	

	public void getFinishButton(ActionEvent event) throws Exception {
			if(true) {
				if(true) {
					System.out.println(identification + " " + fullName + " " + job + " " + gender + " " + birth + " " + email + " " + address);
	                Button clickedButton = (Button) event.getSource();
	                Scene currentScene = clickedButton.getScene();
	                Stage currentStage = (Stage) currentScene.getWindow();
	                toSignIn.SignIn(currentStage);
	                System.out.println("Đăng ký thành công");
	            
			} else {
				Alert notSamePass = new Alert(Alert.AlertType.ERROR);
				notSamePass.setHeaderText("Failed");
				notSamePass.setContentText("The phone number and the username must be the same!");
				notSamePass.show();

			}
		}
		else {
			Alert empty = new Alert(Alert.AlertType.ERROR);
			empty.setHeaderText("Failed");
			empty.setContentText("Please fill in all the information");
			empty.show();
		}	
		
		

	}
	private String convertDateFormat(String inputDate) {
	    String[] parts = inputDate.split("-|/");
	    String[] formattedParts = Arrays.stream(parts)
	            .map(s -> s.length() == 1 ? "0" + s : s)
	            .toArray(size -> new String[size]);

	    String day = formattedParts[0];
	    String month = formattedParts[1];
	    String year = formattedParts[2];

	    return year + "-" + month + "-" + day;
	}

}
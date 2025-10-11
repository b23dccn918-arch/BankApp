package com.mycompany.bankonline.Controller.SignUp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Database.SignUp.NextHandler;
import com.mycompany.bankonline.Database.SignUp.SignUpHandler;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.DisplayScene.toSignUp;
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerSignUp  {

	Account newAcc = new Account();
	User newCustomer = new User();

	@FXML
	private Button BackButton, NextButton, BackButton2, FinishButton;
	@FXML
	private TextField Identification, FullName, Job, Email, Address;
	
	@FXML
    private DatePicker Birth; // ✅ đổi từ TextField sang DatePicker
	
	@FXML
    private ComboBox<String> Gender;
	

	private String phoneNumber, citizenIdentification, job, address;
	
	@FXML
	public void initialize() {
	    Gender.getItems().addAll("Male", "Female");
	}

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
		String gender = Gender.getValue();
		LocalDate birth = Birth.getValue();
		String mail = Email.getText().trim();
		String address = Address.getText().trim();

		// Kiểm tra trống
		if (identification.isEmpty() || fullName.isEmpty() || job.isEmpty()
		        || gender == null || birth == null || mail.isEmpty() || address.isEmpty()) {
		    Alert empty = new Alert(Alert.AlertType.ERROR);
		    empty.setHeaderText("Failed");
		    empty.setContentText("Please fill in all the information");
		    empty.show();
		    return;
		}


		// Kiểm tra email đúng định dạng chưa
		if (!isValidEmail(mail)) {
			Alert invalidEmail = new Alert(Alert.AlertType.ERROR);
			invalidEmail.setHeaderText("Invalid Email");
			invalidEmail.setContentText("Please enter a valid email address");
			invalidEmail.show();
			return;
		}

		// Kiểm tra số căn cước
		if (!NextHandler.isIdentificationExist(identification)) {
			System.out.println("Qua buoc 1");
			System.out.println(identification + " " + fullName + " " + job + " " + gender + " " + birth + " " + mail
					+ " " + address);
			Button clickedButton = (Button) event.getSource();
			Scene currentScene = clickedButton.getScene();
			Stage currentStage = (Stage) currentScene.getWindow();
			try {
				toSignUp.SignUp2(currentStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Alert existed = new Alert(Alert.AlertType.ERROR);
			existed.setTitle("Failed");
			existed.setContentText("The citizen identification number has already been used !");
			existed.show();
		}
	}

	// Hàm kiểm tra định dạng email
	private boolean isValidEmail(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}

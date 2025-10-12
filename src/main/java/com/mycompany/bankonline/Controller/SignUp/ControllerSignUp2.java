package com.mycompany.bankonline.Controller.SignUp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Database.SignUp.FinishHandler;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ControllerSignUp2 {

	@FXML
	private Button BackButton2, FinishButton;

	@FXML
	private PasswordField Password, PinCode, ConfirmPass;

	@FXML
	private TextField PhoneNumber, PaymentAccount;

	@FXML
	private TextField Identification, FullName, Job, Email, Address;

	@FXML
	private DatePicker Birth;

	@FXML
	private ComboBox<String> Gender;

	private String identification, phone, password, fullName, job, email, address, gender;

	LocalDate birth;

	@FXML
	public void initialize() {
		// Giới hạn độ dài của PhoneNumber
		PhoneNumber.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() > 10) {
				PhoneNumber.setText(oldValue); // Không cho nhập thêm khi quá 10 ký tự
			}
		});

		PinCode.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() > 6) {
				PinCode.setText(oldValue); // Không cho nhập thêm khi quá 6 ký tự
			}
		});

		PaymentAccount.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() > 15) {
				PaymentAccount.setText(oldValue); // Không cho nhập thêm khi quá 15 ký tự
			}
		});
	}

	public void BackToSignUp_1(ActionEvent event) throws IOException {

		Button clickedButton = (Button) event.getSource();
		Scene currentScene = clickedButton.getScene();
		Stage currentStage = (Stage) currentScene.getWindow();
		toSignUp.SignUp(currentStage);
	}

	public void setUserData(String identification, String fullName, String job, String gender, LocalDate birth,
			String email, String address) {
		this.identification = identification;
		this.fullName = fullName;
		this.job = job;
		this.gender = gender;
		this.birth = birth;
		this.email = email;
		this.address = address;
	}

	// Hàm kiểm tra mật khẩu đủ mạnh
	private boolean isStrongPassword(String password) {
		// Có ít nhất 1 chữ thường, 1 chữ hoa, 1 số, 1 ký tự đặc biệt và dài it nhat 8
		// ký tự
		String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	public void getFinishButton(ActionEvent event) throws Exception {
		String phoneNum = PhoneNumber.getText();
		String password = Password.getText();
		String pin = PinCode.getText();
		String paymentAccount = PaymentAccount.getText();
		// Ktra mat khau
//		if (!password.equals(ConfirmPass.getText())) {
//			Alert notSame = new Alert(Alert.AlertType.ERROR);
//			notSame.setTitle("Failed");
//			notSame.setContentText("The confirmation password does not match !");
//			notSame.show();
//			return;
//		}
//
//		// Ktra mat khau du manh chua
//		if (!isStrongPassword(password)) {
//			Alert weakPass = new Alert(Alert.AlertType.ERROR);
//			weakPass.setTitle("Failed");
//			weakPass.setHeaderText(null);
//			weakPass.setContentText("Your password is too weak !");
//			weakPass.show();
//			return;
//		}
//
//		if (pin.length() < 6) {
//			Alert pinError = new Alert(Alert.AlertType.ERROR);
//			pinError.setTitle("Failed");
//			pinError.setContentText("PIN code must be exactly 6 digits !");
//			pinError.show();
//			return;
//		}
//
//		if (phoneNum.length() < 10 || phoneNum.charAt(0) != '0') {
//			Alert phoneError = new Alert(Alert.AlertType.ERROR);
//			phoneError.setTitle("Failed");
//			phoneError.setContentText("Invalid phone number !");
//			phoneError.show();
//			return;
//		}
//
//		if (paymentAccount.length() < 8) {
//			Alert paymentAcccountError = new Alert(Alert.AlertType.ERROR);
//			paymentAcccountError.setTitle("Failed");
//			paymentAcccountError.setContentText("Payment account number must have at least 8 digits !");
//			paymentAcccountError.show();
//			return;
//		}

		if (!FinishHandler.isPhoneExist(phoneNum)) {
			System.out.println("Qua buoc 2");
			System.out.println(identification + " " + fullName + " " + job + " " + gender + " " + birth + " " + email
					+ " " + address);
//			FinishHandler.insertUser(fullName, identification, job, gender, birth, address, email);
//			FinishHandler.insertAccount(paymentAccount, FinishHandler.getUserIdByCitizenId(identification), phoneNum, password, 0, pin);
			
			Button clickedButton = (Button) event.getSource();
			Scene currentScene = clickedButton.getScene();
			Stage currentStage = (Stage) currentScene.getWindow();
			try {
				toSignIn.SignIn(currentStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Alert existed = new Alert(Alert.AlertType.ERROR);
			existed.setTitle("Failed");
			existed.setContentText("The phone number has already been used !");
			existed.show();

		}

	}
}
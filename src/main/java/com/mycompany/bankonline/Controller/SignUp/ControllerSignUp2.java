package com.mycompany.bankonline.Controller.SignUp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

import com.mycompany.bankonline.Database.Connect;

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
	private TextField Username, PaymentAccount;
	private String PhoneNumber, Identification, FullName, Job, Email, Address, Birth, Gender;


	public void BackToSignUp_1(ActionEvent event) throws IOException {
	
		Button clickedButton = (Button) event.getSource();
		Scene currentScene = clickedButton.getScene();
		Stage currentStage = (Stage) currentScene.getWindow();
		toSignUp.SignUp(currentStage);
	}

	public void setUserData(String phoneNumber, String Identification, String FullName, String Job, String Gender, String Birth, String Email, String Address) {
		this.PhoneNumber = phoneNumber;
		this.Identification = Identification;
		this.FullName = FullName;
		this.Job = Job;
		this.Gender = Gender;
		this.Birth = Birth;
		this.Email = Email;
		this.Address = Address;

	}
	

	public void getFinishButton(ActionEvent event) throws Exception {
//		String username = Username.getText();
//		String password = Password.getText();
//		String confirmPass = ConfirmPass.getText();
//		String pin = PinCode.getText();
//		String paymentAccountNum = PaymentAccount.getText();
//		boolean allFilled = Arrays.asList(password, confirmPass, pin, paymentAccountNum)
//                .stream()
//                .allMatch(s -> !s.isEmpty());
//
//		if (allFilled) {
//			if (password.equals(confirmPass) && username.equals(PhoneNumber)) {
//				Connection con = Connect.getConnection();
//				con.setAutoCommit(false);
//				try {
//	                // INSERT INTO ACCOUNTS - DÙNG BIẾN stmtAccount
//	                PreparedStatement stmtAccount = con.prepareStatement("INSERT INTO accounts VALUES(?, ?, ?, ?, ?)");
//	                stmtAccount.setString(1, username);
//	                stmtAccount.setString(2, password);
//	                stmtAccount.setString(3, paymentAccountNum);
//	                stmtAccount.setLong(4, 0);
//	                stmtAccount.setString(5, pin);
//	                stmtAccount.executeUpdate();
//	                stmtAccount.close(); // ĐÓNG STATEMENT
//
//	                // INSERT INTO CUSTOMERS - DÙNG BIẾN stmtCustomer
//	                PreparedStatement stmtCustomer = con.prepareStatement("INSERT INTO customers VALUES(?, ?, ?, ?, ?, ?, ?)");
//	                stmtCustomer.setString(1, FullName);
//	                stmtCustomer.setString(2, convertDateFormat(Birth));
//	                stmtCustomer.setString(3, Identification);
//	                stmtCustomer.setString(4, Gender);
//	                stmtCustomer.setString(5, Job);
//	                stmtCustomer.setString(6, Address);
//	                stmtCustomer.setString(7, PhoneNumber);
//	                stmtCustomer.executeUpdate();
//	                stmtCustomer.close(); // ĐÓNG STATEMENT
//
//	                con.commit();
//	                System.out.println("Đăng ký thành công");
//
//	                // Chuyển scene sau khi thành công
//	                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn/SignInInterface.fxml"));
//	                Parent root = loader.load();
//	                Scene scene = new Scene(root);
//	                scene.getStylesheets().add(getClass().getResource("/SignIn/StyleSignIn.css").toExternalForm());
//	                Button clickedButton = (Button) event.getSource();
//	                Scene currentScene = clickedButton.getScene();
//	                Stage currentStage = (Stage) currentScene.getWindow();
//	                currentStage.setTitle("SignIn Scene");
//	                currentStage.setScene(scene);
//	                System.out.println("Đăng ký thành công");
//	            } catch (Exception e) {
//	                con.rollback();
//	                e.printStackTrace(); // IN LỖI ĐỂ DEBUG
//	                
//	            } finally {
//	                if (con != null) con.close(); // LUÔN ĐÓNG KẾT NỐI
//	            }
//				
//
//			} else {
//				Alert notSamePass = new Alert(Alert.AlertType.ERROR);
//				notSamePass.setHeaderText("Failed");
//				notSamePass.setContentText("The phone number and the username must be the same!");
//				notSamePass.show();
//
//			}
//		}
//		else {
//			Alert empty = new Alert(Alert.AlertType.ERROR);
//			empty.setHeaderText("Failed");
//			empty.setContentText("Please fill in all the information");
//			empty.show();
//		}	

		String username = Username.getText();
		String password = Password.getText();
		String confirmPass = ConfirmPass.getText();
		String pin = PinCode.getText();
		String paymentAccountNum = PaymentAccount.getText();
		boolean allFilled = Arrays.asList(password, confirmPass, pin, paymentAccountNum)
                .stream()
                .allMatch(s -> !s.isEmpty());

		if (allFilled) {
			if (password.equals(confirmPass) && username.equals(PhoneNumber)) {
				Connection con = Connect.getConnection();
				con.setAutoCommit(false);
				try {
	                // INSERT INTO ACCOUNTS - DÙNG BIẾN stmtAccount
	                PreparedStatement stmtAccount = con.prepareStatement("INSERT INTO accounts VALUES(?, ?, ?, ?, ?)");
	                stmtAccount.setString(1, username);
	                stmtAccount.setString(2, password);
	                stmtAccount.setString(3, paymentAccountNum);
	                stmtAccount.setLong(4, 0);
	                stmtAccount.setString(5, pin);
	                stmtAccount.executeUpdate();
	                stmtAccount.close(); // ĐÓNG STATEMENT

	                // INSERT INTO CUSTOMERS - DÙNG BIẾN stmtCustomer
	                PreparedStatement stmtCustomer = con.prepareStatement("INSERT INTO customers VALUES(?, ?, ?, ?, ?, ?, ?)");
	                stmtCustomer.setString(1, FullName);
	                stmtCustomer.setString(2, convertDateFormat(Birth));
	                stmtCustomer.setString(3, Identification);
	                stmtCustomer.setString(4, Gender);
	                stmtCustomer.setString(5, Job);
	                stmtCustomer.setString(6, Address);
	                stmtCustomer.setString(7, PhoneNumber);
	                stmtCustomer.executeUpdate();
	                stmtCustomer.close(); // ĐÓNG STATEMENT

	                con.commit();
	                System.out.println("Đăng ký thành công");

	                // Chuyển scene sau khi thành công
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn/SignInInterface.fxml"));
	                Parent root = loader.load();
	                Scene scene = new Scene(root);
	                scene.getStylesheets().add(getClass().getResource("/SignIn/StyleSignIn.css").toExternalForm());
	                Button clickedButton = (Button) event.getSource();
	                Scene currentScene = clickedButton.getScene();
	                Stage currentStage = (Stage) currentScene.getWindow();
	                currentStage.setTitle("SignIn Scene");
	                currentStage.setScene(scene);
	                System.out.println("Đăng ký thành công");
	            } catch (Exception e) {
	                con.rollback();
	                e.printStackTrace(); // IN LỖI ĐỂ DEBUG
	                
	            } finally {
	                if (con != null) con.close(); // LUÔN ĐÓNG KẾT NỐI
	            }
				

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

	    // Dùng Stream để đảm bảo mỗi phần có 2 chữ số (thêm 0 nếu cần)
	    String[] formattedParts = Arrays.stream(parts)
	            .map(s -> s.length() == 1 ? "0" + s : s)
	            .toArray(size -> new String[size]);

	    String day = formattedParts[0];
	    String month = formattedParts[1];
	    String year = formattedParts[2];

	    return year + "-" + month + "-" + day;
	}

}
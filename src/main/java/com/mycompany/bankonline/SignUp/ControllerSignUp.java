// package com.mycompany.bankonline.SignUp;


// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.util.LinkedList;
// import java.util.List;

// import com.mycompany.bankonline.MainApp.Main;
// import com.mycompany.bankonline.Model.Account;

// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Button;
// import javafx.scene.control.PasswordField;
// import javafx.scene.control.TextField;
// import javafx.stage.Stage;

// public class ControllerSignUp {
	
// 	Account newAcc = new Account();
// 	User newCustomer = new Customer();

// 	@FXML
// 	private Button BackButton, NextButton, BackButton2, FinishButton;
// 	@FXML
// 	private TextField PhoneNumber, Identification, FullName, Job, Email, Address, Birth, Gender;
// 	@FXML
// 	private static Alert alert = new Alert(Alert.AlertType.ERROR);
	
// 	private String phoneNumber, citizenIdentification, job, address, referrer;

// 	public void BackToSignIn(ActionEvent event) {
// 		Button clickedButton = (Button) event.getSource();
// 		Scene currentScene = clickedButton.getScene();
// 		Stage currentStage = (Stage) currentScene.getWindow();
// 		try {
// 			Main.SignIn(currentStage); 
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		}
// 	}
	
// 	public void getNextButton(ActionEvent event) throws Exception {
// 	    String phonenumber = PhoneNumber.getText().trim();
// 	    String number = Identification.getText().trim();
// 	    String fullName = FullName.getText().trim();
// 	    String job = Job.getText().trim();
// 	    String gender = Gender.getText().trim();
// 	    String birth = Birth.getText().trim();
// 	    String mail = Email.getText().trim();
// 	    String address = Address.getText().trim();

// 	    if(checkPhoneNumber(phonenumber) && checkIdentification(number)) {
// 	       if(phonenumber.isEmpty() || number.isEmpty() || job.isEmpty() || mail.isEmpty() || address.isEmpty() || fullName.isEmpty() || gender.isEmpty() || birth.isEmpty()) {
// 	    	   Alert empty = new Alert(Alert.AlertType.ERROR);
// 	    	   empty.setHeaderText("Failed");
// 	    	   empty.setContentText("Please fill in all the information");
// 	    	   empty.show();
// 	       }
	       
// 	       else {
// 	    	   System.out.println("Qua buoc 1");

// 		        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpInterface_2.fxml"));
// 		        Parent root = loader.load();


// 		        ControllerSignUp2 controller2 = loader.getController();
// 		        controller2.setUserData(phonenumber, number, fullName, job, gender, birth, mail, address);


// 		        Scene scene = new Scene(root);
// 		        scene.getStylesheets().add(getClass().getResource("/SignUp/StyleSignUp.css").toExternalForm());


// 		        Button clickedButton = (Button) event.getSource();
// 		        Scene currentScene = clickedButton.getScene();
// 		        Stage currentStage = (Stage) currentScene.getWindow();
// 		        currentStage.setScene(scene);
// 	       }

// 	    } else {
// 	        alert.setHeaderText("Failed");
// 	        alert.setContentText("Phone number or Citizen identification number already registered!");
// 	        alert.show();
// 	    }
// 	}


// 	private static boolean checkPhoneNumber(String phonenum) throws Exception {
// 		String queryAccounts = "SELECT * FROM Accounts";
// 		String queryCustomers = "SELECT * FROM Customers";
// 		Connection con = Main.getConnection();
		
		
// 		PreparedStatement stmtAccounts = con.prepareStatement(queryAccounts);
// 		List<String> PhoneNumbersAccounts = new LinkedList<String>();
// 		ResultSet rsAccounts = stmtAccounts.executeQuery();
// 		while (rsAccounts.next()) {
// 			PhoneNumbersAccounts.add(rsAccounts.getString(1));
// 		}
// 		boolean existsAccounts = PhoneNumbersAccounts.stream().anyMatch(u -> u.equalsIgnoreCase(phonenum)); 
		
// 		PreparedStatement stmtCustomers = con.prepareStatement(queryCustomers);
// 		List<String> PhoneNumbersCustomers = new LinkedList<String>();
// 		ResultSet rsCustomers = stmtCustomers.executeQuery();
// 		while (rsCustomers.next()) {
// 			PhoneNumbersCustomers.add(rsCustomers.getString(1));
// 		}
// 		boolean existsCustomers = PhoneNumbersCustomers.stream().anyMatch(u -> u.equalsIgnoreCase(phonenum)); 
		
		
// 		return !existsAccounts && !existsCustomers;
		
		
// 	}
	
// 	private static boolean checkIdentification(String number) throws Exception {
// 		String query = "SELECT * FROM Customers";
// 		Connection con = Main.getConnection();
// 		PreparedStatement stmt = con.prepareStatement(query);
// 		List<String> Identifications = new LinkedList<String>();
// 		ResultSet rs = stmt.executeQuery();
// 		while (rs.next()) {
// 			Identifications.add(rs.getString(3));
// 		}
// 		boolean exists = Identifications.stream().anyMatch(u -> u.equalsIgnoreCase(number)); 
// 		return !exists;
// 	}

// }
package com.mycompany.bankonline.SignIn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mycompany.bankonline.MainApp.*;
import com.mycompany.bankonline.Session.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerSignIn {
	@FXML
	private TextField Username;
	@FXML
	private PasswordField Password;
	@FXML
    private Button SignInButton;
	@FXML
	private Button SignUpButton;
	@FXML
	private static Alert alert = new Alert(Alert.AlertType.INFORMATION);
	
	@FXML
    public void initialize() {
        SignInButton.disableProperty().bind(
                Username.textProperty().isEmpty()
                        .or(Password.textProperty().isEmpty())
        );
    }
	
	public void getLoginInformaton(ActionEvent event) {
		String username = Username.getText();
		String password = Password.getText();
		Button clickedButton = (Button) event.getSource();
		Scene currentScene = clickedButton.getScene();
		Stage currentStage = (Stage) currentScene.getWindow();

		int userId = checkSignIn(username, password);

		
		if(userId!=-1) {
			Session.getInstance().setUser(0, username);
			try {
				Main.DashBoard(currentStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			alert.setHeaderText("Login Success");
	        alert.setContentText("Welcome to BASOUR BANK");
	        alert.show();
		} else {
	        alert.setHeaderText("Login Failed");
	        alert.setContentText("Username or Password is incorrect!");
	        alert.show();
		}
	}
	
	  public void handleSignUpButton(ActionEvent event) {
	        // Lấy stage hiện tại từ nút được nhấn
		  	Button clickedButton = (Button) event.getSource();
		  	Scene currentScene = clickedButton.getScene();
		  	Stage currentStage = (Stage) currentScene.getWindow();
	        try {
	            Main.SignUp(currentStage); // Gọi phương thức SignUp từ Main class
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	private static int checkSignIn(String username, String password) {
		String query = "SELECT user_id FROM users WHERE phone = ? AND password_hash = ?"; 

		Connection con = Main.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("user_id"); // Lấy user_id từ DB
			}
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			alert.setHeaderText("Database Error");
			alert.setContentText("Cannot connect to database!");
			alert.show();
			return -1;
		}
		}
	
}

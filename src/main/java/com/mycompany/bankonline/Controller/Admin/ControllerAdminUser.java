package com.mycompany.bankonline.Controller.Admin;

import java.io.IOException;

import com.mycompany.bankonline.DisplayScene.toAdminDashBoard;
import com.mycompany.bankonline.DisplayScene.toSignUp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ControllerAdminUser {
	@FXML
	private Button LogoutButton, BanButton, UnBanButton, DeleteButton, AccountButton;
	
	@FXML
	private TextField UserID;
	
	
	private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
    @FXML
    private void backToSignIn(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn đăng xuất?");
        alert.showAndWait().ifPresent(response -> {
        if (response == javafx.scene.control.ButtonType.OK) {
            try {
                
                // Lấy stage hiện tại
                Stage stage = (Stage) LogoutButton.getScene().getWindow();
                // Chuyển về trang đăng nhập
                toAdminDashBoard.SignIn(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    }
	
	@FXML
	public void toAccount(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
	  	Scene currentScene = clickedButton.getScene();
	  	Stage currentStage = (Stage) currentScene.getWindow();
        try {
            toAdminDashBoard.Account(currentStage); 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	public void banUser(ActionEvent event) {
		
	}
	
	@FXML
	public void unBanUser(ActionEvent event) {
		
	}
	
	@FXML
	public void deleteUser(ActionEvent event) {
		
	}
}

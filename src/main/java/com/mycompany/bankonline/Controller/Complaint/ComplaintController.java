package com.mycompany.bankonline.Controller.Complaint;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Database.Complaint.ComplaintHandler;
import com.mycompany.bankonline.DisplayScene.toComplaint;
import com.mycompany.bankonline.DisplayScene.toComplaintList;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Session.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ComplaintController implements Initializable  {

    @FXML
    private TextField subjectField;

    @FXML
    private TextArea contentField;

    @FXML
    private Button submitComplaintButton;

    @FXML
    private Button complaintListButton;

    @FXML
    private Label statusLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button accountButton;

    @FXML
    private Button transferButton;

    @FXML
    private Button withdrawButton;

    @FXML
    private Button depositButton;

    @FXML
    private Button paymentButton;

    @FXML
    private Button complaintButton;

    @FXML
    private Button historyButton;

    private final ComplaintHandler complaintHandler = new ComplaintHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.DashBoard(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        accountButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.UserInfo(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        transferButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.Transfer(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        historyButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.History(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        withdrawButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.WithDraw(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        paymentButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.Payment(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        depositButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.Deposit(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        complaintButton.setOnAction(e ->{
        try{
            toComplaint.Complaint((Stage) complaintButton.getScene().getWindow());
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }});
        logoutButton.setOnAction(this::handleLogout);
        submitComplaintButton.setOnAction(this::handleSubmitComplaint);
        complaintListButton.setOnAction(e -> {
            try{
                toComplaintList.ComplaintList((Stage) complaintListButton.getScene().getWindow());
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        });
    }

    private void handleSubmitComplaint(ActionEvent event) {
        String subject = subjectField.getText().trim();
        String content = contentField.getText().trim();

        if (subject.isEmpty() || content.isEmpty()) {
            showMessage("Error", "Vui lòng nhập đầy đủ tiêu đề và nội dung khiếu nại.");
            return;
        }

        int accountId = Session.getInstance().getAccountId();

        Boolean result = complaintHandler.SendComplaint(accountId, subject, content);

        if(result){
            showMessage("Success", "");
            subjectField.clear();
            contentField.clear();
        }
        else{
            showMessage("Error", "");
        }
        
        
    }


    private void showMessage(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void handleViewComplaints(ActionEvent event) {
        showMessage("123", "show message");
    }

    private void clearFields() {
        subjectField.clear();
        contentField.clear();
    }


    private void handleLogout(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out of this account?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Clear current user session
                    Session.getInstance().clear();

                    // Return to sign-in page
                    Stage stage = (Stage) logoutButton.getScene().getWindow();
                    toSignIn.SignIn(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

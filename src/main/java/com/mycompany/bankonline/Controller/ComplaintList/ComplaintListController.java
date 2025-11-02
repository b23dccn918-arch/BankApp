package com.mycompany.bankonline.Controller.ComplaintList;


import com.mycompany.bankonline.Controller.ComplaintDetail.ComplaintDetailController;
import com.mycompany.bankonline.Database.Complaint.ComplaintHandler;
import com.mycompany.bankonline.DisplayScene.toComplaint;
import com.mycompany.bankonline.DisplayScene.toDashBoard;
import com.mycompany.bankonline.DisplayScene.toDeposit;
import com.mycompany.bankonline.DisplayScene.toHistory;
import com.mycompany.bankonline.DisplayScene.toPayment;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.DisplayScene.toTransfer;
import com.mycompany.bankonline.DisplayScene.toUserInfo;
import com.mycompany.bankonline.DisplayScene.toWithdraw;
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Model.Complaint;
import com.mycompany.bankonline.Session.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.geometry.Insets;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintListController {

    @FXML
    private Button backButton;

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

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox complaintContainer;

    private final ComplaintHandler  complaintHandler = new ComplaintHandler();

    @FXML
    public void initialize() {
        homeButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toDashBoard.DashBoard(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        accountButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toUserInfo.UserInfo(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        transferButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toTransfer.Transfer(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        historyButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toHistory.History(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        withdrawButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toWithdraw.WithDraw(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        paymentButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toPayment.Payment(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        depositButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                toDeposit.Deposit(stage); 
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
        backButton.setOnAction(e ->{
        try{
            toComplaint.Complaint((Stage) complaintButton.getScene().getWindow());
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }});
        logoutButton.setOnAction(this::handleLogout);
        int accountId = Session.getInstance().getAccountId();
        List<Complaint> complaints = complaintHandler.getAllComplaintsByAccountId(accountId);
        displayComplaints(complaints);
    }

    private String getStatusStyle(String status) {
        switch (status) {
            case "pending":
                return "-fx-text-fill: #725c06ff;";
            case "resolved":
                return "-fx-text-fill: #27ae60;";
            case "rejected":
                return "-fx-text-fill: #e74c3c;";
            default:
                return "-fx-text-fill: black;";
        }
    }

    private void displayComplaints(List<Complaint> complaints) {
        complaintContainer.getChildren().clear();

        for (Complaint c : complaints) {
            VBox box = new VBox(5);
            box.setPadding(new Insets(10));
            box.setStyle("-fx-background-color: #f7f7f7; -fx-border-color: #cccccc; -fx-border-radius: 8; -fx-background-radius: 8;");

            Label title = new Label("Subject: " + c.getSubject());
            title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            Label status = new Label("Status: " + c.getStatus());
            status.setStyle(getStatusStyle(c.getStatus()));

            Label created = new Label("Day created: " + c.getCreatedAt().toString());
            created.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");

            box.getChildren().addAll(title, status, created);
            VBox.setVgrow(box, Priority.ALWAYS);
            box.setOnMouseClicked(e -> showComplaintDetail(c));
            box.setOnMouseEntered(e -> box.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-radius: 8; -fx-background-radius: 8;"));
            box.setOnMouseExited(e -> box.setStyle("-fx-background-color: #ffffff; -fx-border-color: #dddddd; -fx-border-radius: 8; -fx-background-radius: 8;"));
            complaintContainer.getChildren().add(box);
        }
    }

    private void showComplaintDetail(Complaint complaint) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/ComplaintDetail/ComplaintDetail.fxml"));
            Parent root = loader.load();

            ComplaintDetailController controller = loader.getController();
            controller.setComplaint(complaint);

            Stage detailStage = new Stage();
            detailStage.setTitle("Complaint Detail");
            detailStage.setScene(new Scene(root));
            detailStage.initOwner(scrollPane.getScene().getWindow()); 
            detailStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
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

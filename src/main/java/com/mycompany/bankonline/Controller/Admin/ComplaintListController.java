package com.mycompany.bankonline.Controller.Admin;

import java.io.IOException;
import java.util.List;

import com.mycompany.bankonline.Database.Complaint.ComplaintHandler;
import com.mycompany.bankonline.DisplayScene.toAdminDashBoard;
import com.mycompany.bankonline.Model.Complaint;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComplaintListController {

    @FXML
	private Button LogoutButton, AccountButton, BillButton, AnalyticButton,ComplaintButton;

    @FXML
    private VBox complaintContainer;
    @FXML
    private ScrollPane scrollPane;

    private final ComplaintHandler complaintHandler = new ComplaintHandler();

    @FXML
    public void initialize(){
        List<Complaint> complaints = complaintHandler.getAllComplaints();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/Admin/ComplaintDetail/ComplaintDetail.fxml"));
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

    @FXML
    private void backToSignIn(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out ?");
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
	public void toUser(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
	  	Scene currentScene = clickedButton.getScene();
	  	Stage currentStage = (Stage) currentScene.getWindow();
        try {
            toAdminDashBoard.User(currentStage); 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
    
    @FXML
   	public void toHistory(ActionEvent event) {
   		Button clickedButton = (Button) event.getSource();
   	  	Scene currentScene = clickedButton.getScene();
   	  	Stage currentStage = (Stage) currentScene.getWindow();
           try {
               toAdminDashBoard.History(currentStage); 
           } catch (Exception e) {
               e.printStackTrace();
           }
   	}
    
    @FXML
	public void toBill(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
	  	Scene currentScene = clickedButton.getScene();
	  	Stage currentStage = (Stage) currentScene.getWindow();
        try {
            toAdminDashBoard.Bill(currentStage); 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
    
    @FXML
   	public void toAnalytic(ActionEvent event) {
   		Button clickedButton = (Button) event.getSource();
   	  	Scene currentScene = clickedButton.getScene();
   	  	Stage currentStage = (Stage) currentScene.getWindow();
           try {
               toAdminDashBoard.Analytic(currentStage); 
           } catch (Exception e) {
               e.printStackTrace();
           }
   	}
    
    @FXML
   	public void toComplaint(ActionEvent event) {
   		Button clickedButton = (Button) event.getSource();
   	  	Scene currentScene = clickedButton.getScene();
   	  	Stage currentStage = (Stage) currentScene.getWindow();
           try {
               toAdminDashBoard.ComplaintList(currentStage); 
           } catch (Exception e) {
               e.printStackTrace();
           }
   	}

    

}

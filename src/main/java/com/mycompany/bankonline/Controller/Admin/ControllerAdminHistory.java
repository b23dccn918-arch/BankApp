package com.mycompany.bankonline.Controller.Admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.mycompany.bankonline.Database.Admin.Account.AccountEntity;
import com.mycompany.bankonline.Database.Admin.History.TransactionEntity;
import com.mycompany.bankonline.DisplayScene.toAdminDashBoard;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Model.TransactionAdmin;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControllerAdminHistory {
	@FXML 
    private Button AccountButton, UserButton, LogoutButton;
	
	@FXML
    private TableView<TransactionAdmin> tableHistory;
    @FXML
    private TableColumn<TransactionAdmin, Integer> colTransactionId;
    @FXML
    private TableColumn<TransactionAdmin, String> colFromAccount;
    @FXML
    private TableColumn<TransactionAdmin, String> colToAccount;
    @FXML
    private TableColumn<TransactionAdmin, String> colType;
    @FXML
    private TableColumn<TransactionAdmin, BigDecimal> colAmount;
    @FXML
    private TableColumn<TransactionAdmin, String> colDescription;
    @FXML
    private TableColumn<TransactionAdmin, Timestamp> colCreatedAt;
   
    
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
	
	private void showHistoryTable() {
        ObservableList<TransactionAdmin> list = TransactionEntity.getAllTransactions();

        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transcationId"));
        colFromAccount.setCellValueFactory(new PropertyValueFactory<>("fromAccount"));
        colToAccount.setCellValueFactory(new PropertyValueFactory<>("toAccount"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAd"));

        tableHistory.setItems(list);
    }
    
    @FXML
    public void initialize() {
        showHistoryTable();
    }
}

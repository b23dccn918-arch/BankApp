package com.mycompany.bankonline.Controller.Admin;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mycompany.bankonline.Database.Admin.Account.AccountRepository;
import com.mycompany.bankonline.Database.Admin.Account.AdminAccountFunction;
import com.mycompany.bankonline.DisplayScene.toAdminDashBoard;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Session.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tooltip;

public class AdminAccountController {	
	
	@FXML 
    private Button AccountButton, UserButton, HistoryButton, BillButton, AnalyticButton;
    
    @FXML
    private Button BanButton, UnBanButton, DeleteButton, LogoutButton;
    
    @FXML 
    private TextField AccountID;

	@FXML
    private TableView<Account> tableAccount;
    @FXML
    private TableColumn<Account, Integer> colAccountId;
    @FXML
    private TableColumn<Account, String> colAccountNumber;
    @FXML
    private TableColumn<Account, Integer> colUserId;
    @FXML
    private TableColumn<Account, String> colPhone;
    @FXML
    private TableColumn<Account, String> colPassword;
    @FXML
    private TableColumn<Account, Long> colBalance;
    @FXML
    private TableColumn<Account, Integer> colPin;
    @FXML
    private TableColumn<Account, Integer> colStatus;
    @FXML
    private TableColumn<Account, Timestamp> colCreatedAt;

    
    
    
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
    
    
    private void showAccountTable() {
        ObservableList<Account> list = AccountRepository.getAllAccounts();

        colAccountId.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        colAccountNumber.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colPin.setCellValueFactory(new PropertyValueFactory<>("pinID"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colCreatedAt.setCellFactory(tc -> new TableCell<>() {
		    private final Tooltip tooltip = new Tooltip();

		    @Override
		    protected void updateItem(Timestamp item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText(null);
		            setTooltip(null);
		        } else {
		            String text = item.toString();
		            setText(text.length() > 19 ? text.substring(0, 19) : text); // hiển thị gọn
		            tooltip.setText(text);
		            setTooltip(tooltip);
		        }
		    }
		});

        tableAccount.setItems(list);
    }
    
    @FXML
    public void initialize() {
        showAccountTable();
    }
    
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    @FXML
    private void banAccount(ActionEvent event) {
        String idText = AccountID.getText().trim();

        if (idText.isEmpty()) {
            showAlert(AlertType.WARNING, "Missing Input", "Please enter an Account ID to ban !");
            return;
        }

        try {
            int accountId = Integer.parseInt(idText);
            Account account = AdminAccountFunction.getAccountById(accountId); // lấy account theo ID

            if (account == null) {
                showAlert(AlertType.ERROR, "Not Found", "No account found with ID " + accountId + "!");
                return;
            }

            if (account.getStatus() == 0) {
                showAlert(AlertType.WARNING, "Already Banned", "This account is already banned !");
                return;
            }

            boolean success = AdminAccountFunction.banAccountById(accountId);

            if (success) {
                showAlert(AlertType.INFORMATION, "Success", "Account ID " + accountId + " has been banned successfully.");
                showAccountTable();
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to ban this account !");
            }

        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Invalid Input", "Account ID must be a valid integer !");
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void unBanAccount(ActionEvent event) {
        String idText = AccountID.getText().trim();

        if (idText.isEmpty()) {
            showAlert(AlertType.WARNING, "Missing Input", "Please enter an Account ID to unban !");
            return;
        }

        try {
            int accountId = Integer.parseInt(idText);
            Account account = AdminAccountFunction.getAccountById(accountId);

            if (account == null) {
                showAlert(AlertType.ERROR, "Not Found", "No account found with ID " + accountId + " !");
                return;
            }

            if (account.getStatus() == 1) {
                showAlert(AlertType.WARNING, "Already Active", "This account is already active !");
                return;
            }

            boolean success = AdminAccountFunction.unBanAccount(accountId);

            if (success) {
                showAlert(AlertType.INFORMATION, "Success", "Account ID " + accountId + " has been unbanned successfully.");
                showAccountTable();
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to unban this account !");
            }

        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Invalid Input", "Account ID must be a valid integer !");
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteAccount(ActionEvent event) {
        String idText = AccountID.getText().trim();

        if (idText.isEmpty()) {
            showAlert(AlertType.WARNING, "Missing Input", "Please enter an Account ID to delete !");
            return;
        }

        try {
            int accountId = Integer.parseInt(idText);
            Account account = AdminAccountFunction.getAccountById(accountId);

            if (account == null) {
                showAlert(AlertType.ERROR, "Not Found", "No account found with ID " + accountId + " !");
                return;
            }

            // Hiển thị hộp thoại xác nhận
            Alert confirm = new Alert(AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText(null);
            confirm.setContentText("Are you sure you want to permanently delete Account ID " + accountId + "?");

            // Người dùng chọn YES hoặc NO
            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean success = AdminAccountFunction.deleteAccountById(accountId);

                if (success) {
                    showAlert(AlertType.INFORMATION, "Deleted", "Account ID " + accountId + " has been deleted successfully.");
                    showAccountTable();
                } else {
                    showAlert(AlertType.ERROR, "Error", "Failed to delete this account !");
                }
            } else {
                showAlert(AlertType.INFORMATION, "Cancelled", "Account deletion has been cancelled !");
            }

        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Invalid Input", "Account ID must be a valid integer !");
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


   

}

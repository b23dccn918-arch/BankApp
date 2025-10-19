package com.mycompany.bankonline.Controller.Admin;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mycompany.bankonline.Database.Admin.Account.AdminAccountFunction;
import com.mycompany.bankonline.Database.Admin.User.AdminUserFunction;
import com.mycompany.bankonline.Database.Admin.User.UserEntity;
import com.mycompany.bankonline.DisplayScene.toAdminDashBoard;
import com.mycompany.bankonline.DisplayScene.toSignUp;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ControllerAdminUser {
	@FXML
	private Button LogoutButton, BanButton, UnBanButton, DeleteButton, AccountButton;

	@FXML
	private TextField UserID;

	@FXML
	private TableView<User> tableUser;
	@FXML
	private TableColumn<User, Integer> colUserId;
	@FXML
	private TableColumn<User, String> colFullName;
	@FXML
	private TableColumn<User, String> colCitizienIdentifier;
	@FXML
	private TableColumn<User, String> colJob;
	@FXML
	private TableColumn<User, String> colGender;
	@FXML
	private TableColumn<User, String> colDateBirth;
	@FXML
	private TableColumn<User, String> colAddress;
	@FXML
	private TableColumn<User, String> colEmail;
	@FXML
	private TableColumn<User, String> colCreatedAt;

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

	private void showUserTable() {
		ObservableList<User> list = UserEntity.getAllUsers();
		colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
		colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
		colCitizienIdentifier.setCellValueFactory(new PropertyValueFactory<>("citizenIdentifier"));
		colJob.setCellValueFactory(new PropertyValueFactory<>("job"));
		colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		colDateBirth.setCellValueFactory(new PropertyValueFactory<>("dateBirth"));
		colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

		tableUser.setItems(list);
	}

	@FXML
	public void initialize() {
		showUserTable();
	}

	@FXML
	public void banUser(ActionEvent event) {
		String idText = UserID.getText().trim();

        if (idText.isEmpty()) {
            showAlert(AlertType.WARNING, "Missing Input", "Please enter an Account ID to ban.");
            return;
        }
        try {
        	int userId = Integer.parseInt(idText);
            Account account = AdminUserFunction.getAccountByUserId(userId); // lấy account theo ID
            if (account == null) {
                showAlert(AlertType.ERROR, "Not Found", "No user found with ID " + userId + "!");
                return;
            }

            if (account.getStatus() == 0) {
                showAlert(AlertType.WARNING, "Already Banned", "This user is already banned !");
                return;
            }
            
            boolean success = AdminUserFunction.banAccountByUserId(userId);
            
            if (success) {
                showAlert(AlertType.INFORMATION, "Success", "User ID " + userId + " has been banned successfully.");
                showUserTable();
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to ban this user !");
            }

        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Invalid Input", "User ID must be a valid integer !");
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
	}

	@FXML
	public void unBanUser(ActionEvent event) {
		String idText = UserID.getText().trim();

        if (idText.isEmpty()) {
            showAlert(AlertType.WARNING, "Missing Input", "Please enter an User ID to unban !");
            return;
        }

        try {
            int userId = Integer.parseInt(idText);
            Account account = AdminUserFunction.getAccountByUserId(userId);

            if (account == null) {
                showAlert(AlertType.ERROR, "Not Found", "No account found with ID " + userId + " !");
                return;
            }

            if (account.getStatus() == 1) {
                showAlert(AlertType.WARNING, "Already Active", "This account is already active !");
                return;
            }

            boolean success = AdminUserFunction.unBanAccountByUserId(userId);

            if (success) {
                showAlert(AlertType.INFORMATION, "Success", "User ID " + userId + " has been unbanned successfully.");
                showUserTable();
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to unban this account !");
            }

        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Invalid Input", "User ID must be a valid integer !");
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
	}

	
// dùng lại hàm xóa Account, chỉ cần tìm AccountID từ UserID
	@FXML
	public void deleteUser(ActionEvent event) {
		String idText = UserID.getText().trim();

        if (idText.isEmpty()) {
            showAlert(AlertType.WARNING, "Missing Input", "Please enter an Account ID to delete.");
            return;
        }

        try {
            int userId = Integer.parseInt(idText);
            Account account = AdminUserFunction.getAccountByUserId(userId);
            int accountId = account.getAccountId();

            if (account == null) {
                showAlert(AlertType.ERROR, "Not Found", "No user found with ID " + userId + "!");
                return;
            }

            // Hiển thị hộp thoại xác nhận
            Alert confirm = new Alert(AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText(null);
            confirm.setContentText("Are you sure you want to permanently delete User ID " + userId + "?");

            // Người dùng chọn YES hoặc NO
            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean success = AdminAccountFunction.deleteAccountById(accountId);

                if (success) {
                    showAlert(AlertType.INFORMATION, "Deleted", "User ID " + userId + " has been deleted successfully.");
                    showUserTable();
                } else {
                    showAlert(AlertType.ERROR, "Error", "Failed to delete this user !");
                }
            } else {
                showAlert(AlertType.INFORMATION, "Cancelled", "User deletion has been cancelled !");
            }

        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Invalid Input", "User ID must be a valid integer !");
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
	}
}

package com.mycompany.bankonline.Controller.Admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.mycompany.bankonline.Database.Admin.Account.AdminAccountFunction;
import com.mycompany.bankonline.Database.Admin.PaymentBill.AdminPaymentBillFunction;
import com.mycompany.bankonline.Database.Admin.PaymentBill.PaymentBillRepository;
import com.mycompany.bankonline.DisplayScene.toAdminDashBoard;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Model.BillAdmin;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Tooltip;

public class AdminPaymentBillController {
	@FXML
	private Button AccountButton, UserButton, HistoryButton, AnalyticButton;

	@FXML
	private Button UpdateButton, PaidButton, UnPaidButton, ClearButton, LogoutButton;

	@FXML
	private TextField BillID;

	@FXML
	private TableView<BillAdmin> tableBill;
	@FXML
	private TableColumn<BillAdmin, Long> colBillId;
	@FXML
	private TableColumn<BillAdmin, String> colAccountNumber;
	@FXML
	private TableColumn<BillAdmin, String> colBillType;
	@FXML
	private TableColumn<BillAdmin, BigDecimal> colAmount;
	@FXML
	private TableColumn<BillAdmin, String> colStatus;
	@FXML
	private TableColumn<BillAdmin, Timestamp> colPaidAt;

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

	private void showBillTable() {
		// Lấy dữ liệu từ repository
		ObservableList<BillAdmin> list = PaymentBillRepository.getAllBills();

		// Gán các cột trong bảng
		colBillId.setCellValueFactory(new PropertyValueFactory<>("billId"));
		colAccountNumber.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
		colBillType.setCellValueFactory(new PropertyValueFactory<>("billType"));
		colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		colPaidAt.setCellValueFactory(new PropertyValueFactory<>("paidAt"));
		colPaidAt.setCellFactory(tc -> new TableCell<>() {
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

		// Đưa dữ liệu vào bảng
		tableBill.setItems(list);
	}

	@FXML
	public void initialize() {
		showBillTable();
	}

	private void showAlert(AlertType type, String title, String message) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	public void updateBill(ActionEvent event) {
		String idText = BillID.getText().trim();
		if (idText.isEmpty()) {
			showAlert(AlertType.WARNING, "Missing Input", "Please enter an Bill ID to update !");
			return;
		}
		try {
			int billId = Integer.parseInt(idText);
			BillAdmin bill = AdminPaymentBillFunction.getBillById(billId);
			if (bill == null) {
				showAlert(AlertType.ERROR, "Not Found", "No bill found with ID " + billId + "!");
				return;
			}

			if (bill.getStatus().equals("unpaid")) {
				showAlert(AlertType.ERROR, "Failed", "Invoice pending payment !");
				return;
			}

			boolean success = AdminPaymentBillFunction.updateBill(billId);

			if (success) {
				showAlert(AlertType.INFORMATION, "Success", "Bill ID " + billId + " has been updated successfully.");
				showBillTable();
			} else {
				showAlert(AlertType.ERROR, "Error", "Failed to update this bill !");
				return;
			}
		} catch (NumberFormatException e) {
			showAlert(AlertType.WARNING, "Invalid Input", "Bill ID must be a valid integer !");
		} catch (Exception e) {
			showAlert(AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@FXML
	public void filterPaid(ActionEvent event) {
	    ObservableList<BillAdmin> list = AdminPaymentBillFunction.getBillsByStatus("paid");
	    tableBill.setItems(list);
	}

	@FXML
	public void filterUnPaid(ActionEvent event) {
	    ObservableList<BillAdmin> list = AdminPaymentBillFunction.getBillsByStatus("unpaid");
	    tableBill.setItems(list);
	}
	
	@FXML
	public void clearFilter(ActionEvent event) {
		showBillTable(); // load lại toàn bộ dữ liệu
	}

}

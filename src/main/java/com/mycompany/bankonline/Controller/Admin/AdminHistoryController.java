package com.mycompany.bankonline.Controller.Admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import com.mycompany.bankonline.Database.Admin.History.TransactionRepository;
import com.mycompany.bankonline.DisplayScene.toAdminDashBoard;
import com.mycompany.bankonline.Model.TransactionAdmin;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Tooltip;

public class AdminHistoryController {
	@FXML
	private Button AccountButton, UserButton, BillButton, AnalyticButton;
	
	@FXML
	private Button LogoutButton, FilterButton, ClearFilterButton;

	@FXML
	private DatePicker dateFrom;

	@FXML
	private DatePicker dateTo;

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

	private void showHistoryTable() {
		ObservableList<TransactionAdmin> list = TransactionRepository.getAllTransactions();

		colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		colFromAccount.setCellValueFactory(new PropertyValueFactory<>("fromAccount"));
		colToAccount.setCellValueFactory(new PropertyValueFactory<>("toAccount"));
		colType.setCellValueFactory(new PropertyValueFactory<>("type"));
		colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		colDescription.setCellFactory(tc -> new TableCell<>() {
			private final Tooltip tooltip = new Tooltip();

			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setTooltip(null);
				} else {
					setText(item.length() > 50 ? item.substring(0, 50) + "..." : item);
					tooltip.setText(item);
					setTooltip(tooltip);
				}
			}
		});

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

		tableHistory.setItems(list);
	}

	@FXML
	public void initialize() {
		showHistoryTable();
	}

	@FXML
	public void filterByDate(ActionEvent event) {
		LocalDate from = dateFrom.getValue();
		LocalDate to = dateTo.getValue();

		if (from == null || to == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Filter Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select both 'From' and 'To' dates!");
			alert.show();
			return;
		}

		ObservableList<TransactionAdmin> list = TransactionRepository.getTransactionsByDate(from, to);
		tableHistory.setItems(list);
	}

	@FXML
	public void clearFilter(ActionEvent event) {
		dateFrom.setValue(null);
		dateTo.setValue(null);
		showHistoryTable(); // load lại toàn bộ dữ liệu
	}

}

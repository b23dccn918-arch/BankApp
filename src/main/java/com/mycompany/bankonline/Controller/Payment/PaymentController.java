package com.mycompany.bankonline.Controller.Payment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mycompany.bankonline.Controller.PinDialog.PinDialogController;
import com.mycompany.bankonline.Database.Account.AccountHandler;
import com.mycompany.bankonline.Database.Payment.PaymentHandler;
import com.mycompany.bankonline.DisplayScene.toDashBoard;
import com.mycompany.bankonline.DisplayScene.toDeposit;
import com.mycompany.bankonline.DisplayScene.toHistory;
import com.mycompany.bankonline.DisplayScene.toPayment;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.DisplayScene.toTransfer;
import com.mycompany.bankonline.DisplayScene.toUserInfo;
import com.mycompany.bankonline.DisplayScene.toWithdraw;
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Model.Bill;
import com.mycompany.bankonline.Session.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PaymentController implements Initializable {

    @FXML
    private ComboBox<String> billIdCombo;
    @FXML
    private TableView<Bill> billTable;
    @FXML
    private TableColumn<Bill, String> billIdCol, billTypeCol, billStatusCol, billDueCol, billCreatedCol, billPaidCol;
    @FXML
    private TableColumn<Bill, Double> billAmountCol;
    @FXML
    private Button payBillButton;
    @FXML
    private Button refreshButton;

    private ObservableList<Bill> billList = FXCollections.observableArrayList();

    @FXML
    private Button homeButton;

    @FXML
    private Button accountButton;

    @FXML
    private Button withdrawButton;

    @FXML
    private Button transferButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button paymentButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button depositButton;

    @FXML
    private Label balanceField;

    @FXML
    private ComboBox<String> statusFilterCombo;

    private final AccountHandler accountHandler = new AccountHandler();
    private final PaymentHandler paymentHandler = new PaymentHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Account currentAccount = accountHandler.findAccountByAccountId(Session.getInstance().getAccountId());
        statusFilterCombo.getItems().addAll(
            "All",
            "Paid",
            "Unpaid"
        );

        setupColumns();
        loadData();
        statusFilterCombo.setOnAction(event -> filterBillsByStatus());
        payBillButton.setOnAction(e -> handlePayBill());
        refreshButton.setOnAction(e -> loadData());

        balanceField.setText(String.format("%,d VND", currentAccount.getBalance()));

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
        logoutButton.setOnAction(e -> handleLogout());
    }

    private void setupColumns() {
        billIdCol.setCellValueFactory(new PropertyValueFactory<>("billId"));
        billTypeCol.setCellValueFactory(new PropertyValueFactory<>("billType"));
        billAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        billStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        billDueCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        billCreatedCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        billPaidCol.setCellValueFactory(new PropertyValueFactory<>("paidAt"));

        billStatusCol.setCellFactory(column -> new TableCell<Bill, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                    return;
                }

                String normalized = status.trim().toLowerCase();
                boolean isPaid = normalized.equals("paid");

                setText(isPaid ? "Paid" : "Unpaid");
                setStyle("-fx-text-fill: " + (isPaid ? "green;" : "orange;") + "-fx-font-weight: bold;");
            }
        });
    }

    private void loadData() {
        int accountId = Session.getInstance().getAccountId();
        Account currentAccount = accountHandler.findAccountByAccountId(accountId);
        balanceField.setText(String.format("%,d VND", currentAccount.getBalance()));

        String selected = statusFilterCombo.getValue();

        List<Bill> filteredList = new ArrayList<>();

        if (selected == null || selected.equals("All")) {
            filteredList = paymentHandler.getBillsByAccountId(accountId);
        } else if (selected.equals("Paid")) {
            filteredList = paymentHandler.getBillsByStatus(accountId, "paid");
        } else {
            filteredList = paymentHandler.getBillsByStatus(accountId, "unpaid");
        }

        billTable.setItems(paymentHandler.getBillsByAccountId(accountId));
        billIdCombo.setItems(paymentHandler.getUnpaidBillIds(accountId));
    }

    private void filterBillsByStatus() {
        String selected = statusFilterCombo.getValue();

        List<Bill> filteredList;

        int accountId = Session.getInstance().getAccountId();

        if (selected == null || selected.equals("All")) {
            filteredList = paymentHandler.getBillsByAccountId(accountId);
        } else if (selected.equals("Paid")) {
            filteredList = paymentHandler.getBillsByStatus(accountId, "paid");
        } else {
            filteredList = paymentHandler.getBillsByStatus(accountId, "unpaid");
        }

        billTable.setItems(FXCollections.observableArrayList(filteredList));
    }

    private void handlePayBill() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/bankonline/View/PinDialog/PinDialog.fxml"));
            Parent root = loader.load();

            Stage pinStage = new Stage();
            pinStage.setTitle("Enter PIN Code");
            pinStage.setScene(new Scene(root));
            pinStage.setResizable(false);
            pinStage.initModality(Modality.APPLICATION_MODAL);
            pinStage.showAndWait();

            PinDialogController pinController = loader.getController();
            if (!pinController.isPinConfirmed()) {
                showMessage("Notification", "Transaction cancelled!");
                return;
            }

            String enteredPin = pinController.getEnteredPin();

            int accountId = Session.getInstance().getAccountId();
            Account currentAccount = accountHandler.findAccountByAccountId(accountId);

            String currentAccountPin = currentAccount.getPin();

            if (!enteredPin.equals(currentAccountPin)) {
                showMessage("Notification", "Incorrect PIN!");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error opening PIN dialog.");
            return;
        }

        String selected = billIdCombo.getValue();
        if (selected == null) {
            showAlert("Notification", "Please select a bill to pay!", Alert.AlertType.WARNING);
            return;
        }

        long billId = Long.parseLong(selected);
        int accountId = Session.getInstance().getAccountId();

        int resultCode = paymentHandler.payBill(accountId, billId);

        switch (resultCode) {
            case 0:
                showAlert("Success", "Bill #" + billId + " has been paid successfully!", Alert.AlertType.INFORMATION);
                break;
            case 1:
                showAlert("Insufficient Balance", "Your account balance is not enough to pay this bill.", Alert.AlertType.WARNING);
                break;
            case 2:
                showAlert("Already Paid", "Bill #" + billId + " has already been paid.", Alert.AlertType.INFORMATION);
                break;
            case 3:
                showAlert("Not Found", "Bill or account not found!", Alert.AlertType.ERROR);
                break;
            case 99:
                showAlert("System Error", "An error occurred during payment. Please try again!", Alert.AlertType.ERROR);
                break;
            default:
                showAlert("Unknown", "Unexpected result. Error code: " + resultCode, Alert.AlertType.ERROR);
                break;
        }

        loadData();
    }

    private void showMessage(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Transaction Error");
        alert.setHeaderText("Transaction failed");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleLogout() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");
        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                try {
                    Session.getInstance().clear();
                    Stage stage = (Stage) logoutButton.getScene().getWindow();
                    toSignIn.SignIn(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

package com.mycompany.bankonline.Controller.Payment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mycompany.bankonline.Database.Account.AccountHandler;
import com.mycompany.bankonline.Database.Payment.PaymentHandler;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Model.Bill;
import com.mycompany.bankonline.Session.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class PaymentController implements Initializable {

    @FXML
    private ComboBox<String> billIdCombo;
    @FXML
    private TableView<Bill> billTable;
    @FXML
    private TableColumn<Bill, String> billIdCol, billTypeCol, billStatusCol, billDueCol, billCreatedCol;
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

        statusFilterCombo.getItems().addAll(
            "Tất cả",
            "Đã thanh toán",
            "Chưa thanh toán"
        );

        setupColumns();
        loadData();
        // statusFilterCombo.setOnAction(event -> filterBillsByStatus());
        payBillButton.setOnAction(e -> handlePayBill());
        refreshButton.setOnAction(e -> loadData());

        balanceField.setText(String.format("%,.0f VND", accountHandler.getBalanceByAccountId(Session.getInstance().getAccountId())));

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
        logoutButton.setOnAction(e -> handleLogout());

    }

    private void setupColumns() {
        billIdCol.setCellValueFactory(data -> data.getValue().billIdProperty());
        billTypeCol.setCellValueFactory(data -> data.getValue().billTypeProperty());
        billAmountCol.setCellValueFactory(data -> data.getValue().amountProperty().asObject());
        billStatusCol.setCellValueFactory(data -> data.getValue().statusProperty());
        billDueCol.setCellValueFactory(data -> data.getValue().dueDateProperty());
        billCreatedCol.setCellValueFactory(data -> data.getValue().createdAtProperty());
    }


    // private void filterBillsByStatus() {
    //     String selected = statusFilterCombo.getValue();

    //     List<Bill> filteredList;
        
    //     int accountId = Session.getInstance().getAccountId();

    //     if (selected == null || selected.equals("Tất cả")) {
    //         filteredList = paymentHandler.getBillsByAccountId(accountId);
    //     } else if (selected.equals("Đã thanh toán")) {
    //         filteredList = paymentHandler.getBillsByStatus(accountId, "paid");
    //     } else {
    //         filteredList = paymentHandler.getBillsByStatus(accountId, "pending");
    //     }

    //     billTable.setItems(FXCollections.observableArrayList(filteredList));
    // }

    private void loadData() {
        int accountId = Session.getInstance().getAccountId();
        balanceField.setText(String.format("%,.0f VND", accountHandler.getBalanceByAccountId(accountId)));
        billTable.setItems(paymentHandler.getBillsByAccountId(accountId));
        billIdCombo.setItems(paymentHandler.getUnpaidBillIds(accountId));
    }


    private void handlePayBill() {
        String selected = billIdCombo.getValue();
        if (selected == null) {
            showAlert("Thông báo", "Vui lòng chọn hóa đơn cần thanh toán!", Alert.AlertType.WARNING);
            return;
        }

        long billId = Long.parseLong(selected);
        int accountId = Session.getInstance().getAccountId();


        int resultCode = paymentHandler.payBill(accountId, billId);

        switch (resultCode) {
            case 0:
                showAlert("Thành công", "Thanh toán hoá đơn #" + billId + " thành công!", Alert.AlertType.INFORMATION);
                break;
            case 1:
                showAlert("Không đủ số dư", "Số dư của bạn không đủ để thanh toán hoá đơn này.", Alert.AlertType.WARNING);
                break;
            case 2:
                showAlert("Đã thanh toán", "Hóa đơn #" + billId + " đã được thanh toán trước đó.", Alert.AlertType.INFORMATION);
                break;
            case 3:
                showAlert("Không tìm thấy", "Không tìm thấy thông tin hóa đơn hoặc tài khoản!", Alert.AlertType.ERROR);
                break;
            case 99:
                showAlert("Lỗi hệ thống", "Đã xảy ra lỗi trong quá trình thanh toán. Vui lòng thử lại!", Alert.AlertType.ERROR);
                break;
            default:
                showAlert("Không xác định", "Kết quả không xác định. Mã lỗi: " + resultCode, Alert.AlertType.ERROR);
                break;
        }

        loadData(); // reload lại danh sách hóa đơn
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

    private void handleLogout() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn đăng xuất?");
        alert.showAndWait().ifPresent(response -> {
        if (response == javafx.scene.control.ButtonType.OK) {
            try {

                //them tinh nang xoa sessions hien tai thong tin user (authentication)
                Session.getInstance().clear();
                // Lấy stage hiện tại
                Stage stage = (Stage) logoutButton.getScene().getWindow();
                // Chuyển về trang đăng nhập
                toSignIn.SignIn(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    }

}

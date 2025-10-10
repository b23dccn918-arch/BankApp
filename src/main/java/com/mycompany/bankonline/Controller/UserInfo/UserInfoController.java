package com.mycompany.bankonline.Controller.UserInfo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.mycompany.bankonline.Database.UserInfo.UserInfoHandler;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.MainApp.Main;
import com.mycompany.bankonline.Model.User;
import com.mycompany.bankonline.Session.Session;

public class UserInfoController implements Initializable {

    @FXML
    private Button homeButton;

    @FXML
    private Button accountButton;

    @FXML
    private Button transferButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button withdrawButton;


    @FXML
    private Button logoutButton;

    @FXML
    private Button paymentButton;

    @FXML
    private Button depositButton;

    @FXML
    private Label fullNameLabel;
    @FXML
    private Label citizenIdLabel;
    @FXML
    private Label jobLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label dateBirthLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label createdAtLabel;

    private final UserInfoHandler userHandler = new UserInfoHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Gán sự kiện cho các nút
        loadUserInfo(Session.getInstance().getUserId());
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

    private void loadUserInfo(int userId) {
        User user = userHandler.getUserById(userId);
        if (user != null) {
            fullNameLabel.setText(user.getFullName());
            citizenIdLabel.setText(user.getCitizenIdentifier());
            jobLabel.setText(user.getJob());
            genderLabel.setText(user.getGender());
            dateBirthLabel.setText(formatDate(user.getDateBirth()));
            addressLabel.setText(user.getAddress());
            emailLabel.setText(user.getEmail());
            createdAtLabel.setText(formatDate(user.getCreatedAt()));
        }
    }

    private String formatDate(java.sql.Timestamp timestamp) {
        if (timestamp == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(timestamp);
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

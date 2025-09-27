/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.bankonline.DashBoard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.mycompany.bankonline.MainApp.Main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class DashBoardController implements Initializable {

    @FXML
    private Button homeButton;

    @FXML
    private Button accountButton;

    @FXML
    private Button transferButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button cardButton;

    @FXML
    private Button logoutButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Gán sự kiện cho các nút
        homeButton.setOnAction(e -> showMessage("🏠 Home", "Bạn đang ở trang Home."));
        accountButton.setOnAction(e -> showMessage("💳 Account", "Mở trang Tài khoản."));
        transferButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) transferButton.getScene().getWindow();
                Main.Transfer(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        historyButton.setOnAction(e -> showMessage("📜 History", "Mở trang Lịch sử giao dịch."));
        cardButton.setOnAction(e -> showMessage("💳 Card", "Mở trang Quản lý thẻ."));
        logoutButton.setOnAction(e -> handleLogout());
    }

    private void showMessage(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void handleLogout() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn đăng xuất?");
        alert.showAndWait();
        // TODO: thực hiện logout, quay lại màn hình login
    } 
    
}

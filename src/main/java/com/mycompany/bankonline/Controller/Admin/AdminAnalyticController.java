package com.mycompany.bankonline.Controller.Admin;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.mycompany.bankonline.DisplayScene.toAdminDashBoard;
import com.mycompany.bankonline.Database.Admin.Analytic.AdminAnalyticRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class AdminAnalyticController implements Initializable {
	
    @FXML
    private Button AccountButton, UserButton, HistoryButton, BillButton;

    @FXML
    private Button LogoutButton;

    @FXML
    private PieChart accountStatusChart;

    @FXML
    private PieChart billChart;

    @FXML
    private BarChart<String, Number> transactionChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private AdminAnalyticRepository analyticRepo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        analyticRepo = new AdminAnalyticRepository();
        loadAccountStatusChart();
        loadBillTypeChart();
        loadTransactionChart();
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
                    Stage stage = (Stage) LogoutButton.getScene().getWindow();
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


    private void loadAccountStatusChart() {
        Map<String, Integer> data = analyticRepo.getAccountStatusCount();
        accountStatusChart.getData().clear();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            accountStatusChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        accountStatusChart.setTitle("Trạng thái tài khoản");
    }


    private void loadBillTypeChart() {
        Map<String, Double> billData = analyticRepo.getBillAmountByType();

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : billData.entrySet()) {
            data.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        billChart.setData(data);
        billChart.setTitle("Các loại hóa đơn");
        billChart.setLegendVisible(true);
        billChart.setClockwise(true);
        billChart.setLabelsVisible(true);
        billChart.setStartAngle(90);
    }


    private void loadTransactionChart() {
        Map<String, Double> data = analyticRepo.getTransactionTotalByMonth();
        transactionChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Tổng giao dịch");

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        transactionChart.getData().add(series);
    }
}

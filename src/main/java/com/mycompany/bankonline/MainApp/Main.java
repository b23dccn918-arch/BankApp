	package com.mycompany.bankonline.MainApp;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mycompany.bankonline.DisplayScene.toAdminDashBoard;

import com.mycompany.bankonline.DisplayScene.toDashBoard;
import com.mycompany.bankonline.DisplayScene.toSignIn;
import com.mycompany.bankonline.DisplayScene.toSignUp;
import com.mycompany.bankonline.DisplayScene.toAdminDashBoard;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        // Mặc định khi chạy app thì mở SignIn
        toSignIn.SignIn(primaryStage);
    }

    // ⚡ Để ngoài start()

    public static void UserInfo(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/View/UserInfo/UserInfo.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/UserInfo/UserInfo.css").toExternalForm());
        stage.setTitle("Account Scene");
        stage.setScene(scene);
        stage.show();
    }

    public static void DashBoard(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/View/DashBoard/DashBoard2.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/DashBoard/dashboard.css").toExternalForm());
        stage.setTitle("DashBoard Scene");
        stage.setScene(scene);
        stage.show();
    }

    public static void Transfer(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/View/Transfer/Transfer.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/Transfer/transfer.css").toExternalForm());
        stage.setTitle("DashBoard Scene");
        stage.setScene(scene);
        stage.show();
    }

    public static void History(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/View/History/History.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/History/History.css").toExternalForm());
        stage.setTitle("DashBoard Scene");
        stage.setScene(scene);
        stage.show();
    }
    

    public static void Payment(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/View/Payment/Payment.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/Payment/Payment.css").toExternalForm());
        stage.setTitle("SignUp Scene");
        stage.setScene(scene);
        stage.show();
    }

    public static void WithDraw(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/View/Withdraw/Withdraw.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/Withdraw/Withdraw.css").toExternalForm());
        stage.setTitle("SignUp Scene");
        stage.setScene(scene);
        stage.show();
    }

    public static void Deposit(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/View/Deposit/Deposit.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/Deposit/Deposit.css").toExternalForm());
        stage.setTitle("SignUp Scene");
        stage.setScene(scene);
        stage.show();
    } 

    public static void main(String[] args) {
        launch(args);
    }
}

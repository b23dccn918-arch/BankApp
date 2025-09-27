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

public class Main extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/bank";
    private static final String DB_USER = "bankuser"; // đổi cho phù hợp
    private static final String DB_PASS = "1234"; // đổi cho phù hợp

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // trả về null nếu lỗi
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Mặc định khi chạy app thì mở SignIn
        SignIn(primaryStage);
    }

    // ⚡ Để ngoài start()
    public static void SignIn(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/SignIn/SignInInterface.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/SignIn/StyleSignIn.css").toExternalForm());
        stage.setTitle("SignIn Scene");
        stage.setScene(scene);
        stage.show();
    }

    public static void SignUp(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/SignUp/SignUpInterface.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/SignUp/StyleSignUp.css").toExternalForm());
        stage.setTitle("SignUp Scene");
        stage.setScene(scene);
        stage.show();
    }

    public static void Card(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/Card/Card.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/Card/Card.css").toExternalForm());
        stage.setTitle("Card Scene");
        stage.setScene(scene);
        stage.show();
    }

    public static void Account(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/Account/Account.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/Account/Account.css").toExternalForm());
        stage.setTitle("Account Scene");
        stage.setScene(scene);
        stage.show();
    }

    public static void DashBoard(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/DashBoard/DashBoard2.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/DashBoard/dashboard.css").toExternalForm());
        stage.setTitle("DashBoard Scene");
        stage.setScene(scene);
        stage.show();
    }

    public static void Transfer(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/Transfer/Transfer.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/Transfer/transfer.css").toExternalForm());
        stage.setTitle("DashBoard Scene");
        stage.setScene(scene);
        stage.show();
    }

    public static void History(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/History/History.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/History/History.css").toExternalForm());
        stage.setTitle("DashBoard Scene");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void SignUp2(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/SignUpInterface_2.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/StyleSignUp.css").toExternalForm());
        stage.setTitle("SignUp Scene");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

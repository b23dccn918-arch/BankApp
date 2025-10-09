package com.mycompany.bankonline.DisplayScene;

import java.io.IOException;

import com.mycompany.bankonline.MainApp.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class toSignUp {
    public static void SignUp(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/View/SignUp/SignUpInterface.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/SignUp/StyleSignUp.css").toExternalForm());
        stage.setTitle("SignUp Scene");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void SignUp2(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/View/SignUp/SignUpInterface_2.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/SignUp/StyleSignUp.css").toExternalForm());
        stage.setTitle("SignUp Scene");
        stage.setScene(scene);
        stage.show();
    }
} 


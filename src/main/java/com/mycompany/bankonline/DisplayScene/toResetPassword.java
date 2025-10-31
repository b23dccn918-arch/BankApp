package com.mycompany.bankonline.DisplayScene;

import java.io.IOException;

import com.mycompany.bankonline.MainApp.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class toResetPassword {
    public static void ConfirmToken(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/View/ResetPassword/ResetPassword.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/ResetPassword/ResetPassword.css").toExternalForm());
        stage.setTitle("ResetPassword Scene");
        stage.setScene(scene);
        stage.show();
    }
}

package com.mycompany.bankonline.DisplayScene;

import java.io.IOException;

import com.mycompany.bankonline.MainApp.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class toConfirmToken {
    public static void ConfirmToken(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/View/ConfirmToken/ConfirmToken.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/ConfirmToken/ConfirmToken.css").toExternalForm());
        stage.setTitle("ConfirmToken Scene");
        stage.setScene(scene);
        stage.show();
    }
}

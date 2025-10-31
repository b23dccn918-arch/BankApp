package com.mycompany.bankonline.DisplayScene;

import java.io.IOException;

import com.mycompany.bankonline.MainApp.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class toTransfer {
    public static void Transfer(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/View/Transfer/Transfer.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/Transfer/transfer.css").toExternalForm());
        stage.setTitle("DashBoard Scene");
        stage.setScene(scene);
        stage.show();
    }
}

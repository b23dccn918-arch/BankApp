package com.mycompany.bankonline.DisplayScene;

import java.io.IOException;

import com.mycompany.bankonline.MainApp.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class toSignIn {
    public static void SignIn(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/SignIn/SignInInterface.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/SignIn/StyleSignIn.css").toExternalForm());
        stage.setTitle("SignIn Scene");
        stage.setScene(scene);
        stage.show();
    }
}

package com.mycompany.bankonline.Scene;

import java.io.IOException;

import com.mycompany.bankonline.MainApp.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class toSignUp {
    public static void SignUp(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/SignUp/SignUpInterface.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/SignUp/StyleSignUp.css").toExternalForm());
        stage.setTitle("SignUp Scene");
        stage.setScene(scene);
        stage.show();
    }
}

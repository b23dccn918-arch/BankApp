package com.mycompany.bankonline.DisplayScene;

import java.io.IOException;

import com.mycompany.bankonline.MainApp.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class toAdminDashBoard {
	public static void DashBoard(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/View/Admin/AdminAccountDashBoard.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/Admin/StyleAdminDashBoard.css").toExternalForm());
        stage.setTitle("DashBoard Scene");
        stage.setScene(scene);
        stage.show();
    }
	
	public static void SignIn(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/mycompany/bankonline/View/Admin/AdminSignIn.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/com/mycompany/bankonline/View/Admin/StyleAdminSignIn.css").toExternalForm());
        stage.setTitle("DashBoard Scene");
        stage.setScene(scene);
        stage.show();
    }
}

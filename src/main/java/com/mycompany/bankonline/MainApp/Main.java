package com.mycompany.bankonline.MainApp;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

import com.mycompany.bankonline.DisplayScene.toSignIn;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        // Mặc định khi chạy app thì mở SignIn
        toSignIn.SignIn(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

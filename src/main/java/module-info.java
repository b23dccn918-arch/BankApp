module com.mycompany.bankonline {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    
    exports com.mycompany.bankonline.DashBoard;
    exports com.mycompany.bankonline.MainApp;
    exports com.mycompany.bankonline.SignIn;
    exports com.mycompany.bankonline.SignUp;
    opens com.mycompany.bankonline.MainApp to javafx.fxml;
    opens com.mycompany.bankonline.SignIn to javafx.fxml;
    opens com.mycompany.bankonline.SignUp to javafx.fxml;
    opens com.mycompany.bankonline.DashBoard to javafx.fxml;
    opens com.mycompany.bankonline.Transfer to javafx.fxml; 
}

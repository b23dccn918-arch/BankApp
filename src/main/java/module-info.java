module com.mycompany.bankonline {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports com.mycompany.bankonline.Transfer;
    opens com.mycompany.bankonline.Transfer to javafx.fxml;
    exports com.mycompany.bankonline.History;
    opens com.mycompany.bankonline.History to javafx.fxml;
    exports com.mycompany.bankonline.Account;
    opens com.mycompany.bankonline.Account to javafx.fxml;
    opens com.mycompany.bankonline.Card to javafx.fxml;
    exports com.mycompany.bankonline.DashBoard;
    exports com.mycompany.bankonline.MainApp;
    exports com.mycompany.bankonline.SignIn;
    exports com.mycompany.bankonline.SignUp;
    opens com.mycompany.bankonline.MainApp to javafx.fxml;
    opens com.mycompany.bankonline.SignIn to javafx.fxml;
    opens com.mycompany.bankonline.SignUp to javafx.fxml;
    opens com.mycompany.bankonline.DashBoard to javafx.fxml;
}

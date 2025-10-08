module com.mycompany.bankonline {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    
    opens com.mycompany.bankonline.Controller.SignIn to javafx.fxml;
    opens com.mycompany.bankonline.MainApp to javafx.fxml;
    opens com.mycompany.bankonline.Controller.DashBoard to javafx.fxml;
    opens com.mycompany.bankonline.Controller.UserInfo to javafx.fxml;
    opens com.mycompany.bankonline.Controller.Transfer to javafx.fxml;
    opens com.mycompany.bankonline.Controller.History to javafx.fxml;
    opens com.mycompany.bankonline.Controller.SignUp to javafx.fxml;
    opens com.mycompany.bankonline.Controller.Withdraw to javafx.fxml;
    opens com.mycompany.bankonline.Controller.Payment to javafx.fxml;
    opens com.mycompany.bankonline.Controller.Deposit to javafx.fxml;
    exports com.mycompany.bankonline.Controller.DashBoard;
    exports com.mycompany.bankonline.Controller.UserInfo;
    exports com.mycompany.bankonline.Controller.Transfer;
    exports com.mycompany.bankonline.Controller.History;
    exports com.mycompany.bankonline.Controller.SignIn;
    exports com.mycompany.bankonline.Controller.SignUp;
    exports com.mycompany.bankonline.Controller.Withdraw;
    exports com.mycompany.bankonline.Controller.Payment;
    exports com.mycompany.bankonline.Controller.Deposit;
    exports com.mycompany.bankonline.MainApp;
    exports com.mycompany.bankonline.Model;
    opens com.mycompany.bankonline.Model to javafx.base;
    


}

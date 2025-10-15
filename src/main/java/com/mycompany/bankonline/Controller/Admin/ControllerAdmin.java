package com.mycompany.bankonline.Controller.Admin;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.mycompany.bankonline.Database.Admin.AccountEntity;
import com.mycompany.bankonline.Model.Account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerAdmin {

	@FXML
    private TableView<Account> tableAccount;
    @FXML
    private TableColumn<Account, Integer> colAccountId;
    @FXML
    private TableColumn<Account, String> colAccountNumber;
    @FXML
    private TableColumn<Account, Integer> colUserId;
    @FXML
    private TableColumn<Account, String> colPhone;
    @FXML
    private TableColumn<Account, String> colPassword;
    @FXML
    private TableColumn<Account, Long> colBalance;
    @FXML
    private TableColumn<Account, Integer> colPin;
    @FXML
    private TableColumn<Account, Integer> colStatus;
//    @FXML
//    private TableColumn<Account, String> colCreatedAt;

    
    
    

    private void showAccountTable() {
        ObservableList<Account> list = AccountEntity.getAllAccounts();

        colAccountId.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        colAccountNumber.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colPin.setCellValueFactory(new PropertyValueFactory<>("pinID"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
//        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        tableAccount.setItems(list);
    }
    
    @FXML
    public void initialize() {
        showAccountTable();
    }
	

}

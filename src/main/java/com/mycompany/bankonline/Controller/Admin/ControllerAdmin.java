package com.mycompany.bankonline.Controller.Admin;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import com.mycompany.bankonline.Model.Account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControllerAdmin implements Initializable {

	@FXML
	private TableView<Account> table;
	
	@FXML 
	private TableColumn<Account, Integer> accountId;
	@FXML 
	private TableColumn<Account, String> accountNumber;
	@FXML 
	private TableColumn<Account, Integer> userId;
	@FXML 
	private TableColumn<Account, String> phone;
	@FXML 
	private TableColumn<Account, String> password;
	@FXML 
	private TableColumn<Account, Double> balance;
	@FXML 
	private TableColumn<Account, String> pin;
	@FXML 
	private TableColumn<Account, Integer> status;
	
	private ObservableList<Account> accountList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		accountList = FXCollections.observableArrayList();
	}
	
	

}

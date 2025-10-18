package com.mycompany.bankonline.Database.Admin.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Model.Account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AccountEntity {
	public static ObservableList<Account> getAllAccounts(){
		Connection con = Connect.getConnection();
		String sql = "select * from accounts";
		ObservableList<Account> accounts = FXCollections.observableArrayList();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int accountId = rs.getInt("account_id");
				String username = rs.getString("phone");
				String password = rs.getString("password");
				int userId = rs.getInt("user_id");
				String accountNumber = rs.getString("account_number");
				long balance = (long)(rs.getInt("balance"));
				String pinID = rs.getString("pin");
				int status = rs.getInt("status");
				Timestamp createdAt = rs.getTimestamp("created_at");
				Account account = new Account(accountId, username, password, userId, 
                        accountNumber, balance, pinID, status, createdAt);
				accounts.add(account);
			}
			ps.close();
	        rs.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return  accounts;
		
	}
}

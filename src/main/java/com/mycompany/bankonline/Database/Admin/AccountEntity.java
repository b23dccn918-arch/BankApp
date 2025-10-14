package com.mycompany.bankonline.Database.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Model.Account;

public class AccountEntity {
	public List<Account> getAccount(){
		Connection con = Connect.getConnection();
		String sql = "select * from accounts";
		List<Account> accounts = new ArrayList<Account>();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int accountId = rs.getInt("account_id");
				String username = rs.getString("phone");
				String password = rs.getString("password");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return accounts;
		
	}
}

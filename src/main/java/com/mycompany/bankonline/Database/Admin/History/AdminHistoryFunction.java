package com.mycompany.bankonline.Database.Admin.History;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Model.Account;

public class AdminHistoryFunction {
	public static Account getAccount(int id) {
	    String sql = "SELECT * FROM accounts WHERE account_id = ?";
	    try (Connection conn = Connect.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Account acc = new Account();
	            acc.setAccountId(rs.getInt("account_id"));
	            return acc;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}

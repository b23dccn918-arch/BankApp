package com.mycompany.bankonline.Database.Admin.History;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Model.TransactionAdmin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TransactionRepository {
	public static ObservableList<TransactionAdmin> getAllTransactions(){
		Connection con = Connect.getConnection();
		String sql = "select * from transactions";
		ObservableList<TransactionAdmin> transactions = FXCollections.observableArrayList();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				long transactionId = rs.getLong("transaction_id");
				int fromAccountId = rs.getInt("from_account_id");
				String fromAccount = AdminHistoryFunction.getAccount(fromAccountId).getAccountNumber();
				int toAccountId = rs.getInt("to_account_id");
				String toAccount = AdminHistoryFunction.getAccount(toAccountId).getAccountNumber();
				String type = rs.getString("type");
				BigDecimal amount = rs.getBigDecimal("amount");
				String description = rs.getString("description");
				Timestamp createdAt = rs.getTimestamp("created_at");
				transactions.add(new TransactionAdmin(transactionId, fromAccount, toAccount, type, amount, description, createdAt));
				
			}
			ps.close();
	        rs.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return transactions;
		
	}
}

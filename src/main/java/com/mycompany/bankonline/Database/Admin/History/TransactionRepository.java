package com.mycompany.bankonline.Database.Admin.History;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Model.TransactionAdmin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TransactionRepository {
	public static ObservableList<TransactionAdmin> getAllTransactions() {
		Connection con = Connect.getConnection();
		String sql = "select * from transactions";
		ObservableList<TransactionAdmin> transactions = FXCollections.observableArrayList();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				long transactionId = rs.getLong("transaction_id");
				int fromAccountId = rs.getInt("from_account_id");
				String fromAccount = AdminHistoryFunction.getAccount(fromAccountId).getAccountNumber();
				int toAccountId = rs.getInt("to_account_id");
				Account toAcc = AdminHistoryFunction.getAccount(toAccountId);
				String toAccount = "";
				if (toAcc != null) {
					toAccount = toAcc.getAccountNumber();
				}
				String type = rs.getString("type");
				BigDecimal amount = rs.getBigDecimal("amount");
				String description = rs.getString("description");
				Timestamp createdAt = rs.getTimestamp("created_at");
				transactions.add(new TransactionAdmin(transactionId, fromAccount, toAccount, type, amount, description,
						createdAt));

			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactions;

	}

	public static ObservableList<TransactionAdmin> getTransactionsByDate(LocalDate from, LocalDate to) {
		Connection con = Connect.getConnection();
		String sql = "SELECT * FROM transactions WHERE DATE(created_at) BETWEEN ? AND ?";
		ObservableList<TransactionAdmin> transactions = FXCollections.observableArrayList();

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setDate(1, Date.valueOf(from));
			ps.setDate(2, Date.valueOf(to));

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				long transactionId = rs.getLong("transaction_id");
				int fromAccountId = rs.getInt("from_account_id");
				String fromAccount = AdminHistoryFunction.getAccount(fromAccountId).getAccountNumber();

				int toAccountId = rs.getInt("to_account_id");
				String toAccount = "";
				Account toAcc = AdminHistoryFunction.getAccount(toAccountId);
				if (toAcc != null) {
					toAccount = toAcc.getAccountNumber();
				}

				String type = rs.getString("type");
				BigDecimal amount = rs.getBigDecimal("amount");
				String description = rs.getString("description");
				Timestamp createdAt = rs.getTimestamp("created_at");

				transactions.add(new TransactionAdmin(transactionId, fromAccount, toAccount, type, amount, description,
						createdAt));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactions;
	}

}

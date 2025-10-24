package com.mycompany.bankonline.Database.Admin.PaymentBill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Model.BillAdmin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminPaymentBillFunction {
	public static Account getAccount(int id) {
		String sql = "SELECT * FROM accounts WHERE account_id = ?";
		try (Connection conn = Connect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Account acc = new Account();
				acc.setAccountNumber(rs.getString("account_number"));
				return acc;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BillAdmin getBillById(int id) {
		String sql = "SELECT * FROM payment_bills WHERE bill_id = ?";
		try (Connection conn = Connect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				BillAdmin bill = new BillAdmin();
				bill.setStatus(rs.getString("status"));
				return bill;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean updateBill(int id) {
		String sql = "UPDATE payment_bills SET status = 'unpaid', paid_at = NULL where bill_id = ? AND status = 'paid' ";
		try (Connection conn = Connect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public static ObservableList<BillAdmin> getBillsByStatus(String status) {
	    ObservableList<BillAdmin> list = FXCollections.observableArrayList();
	    String sql = "SELECT * FROM payment_bills WHERE status = ?";
	    try (Connection conn = Connect.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, status);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            BillAdmin bill = new BillAdmin();
	            bill.setBillId(rs.getLong("bill_id"));
//	            bill.setAccountNumber(rs.getString("account_number"));
	            bill.setBillType(rs.getString("bill_type"));
	            bill.setAmount(rs.getBigDecimal("amount"));
	            bill.setStatus(rs.getString("status"));
	            bill.setPaidAt(rs.getTimestamp("paid_at"));
	            list.add(bill);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}

}

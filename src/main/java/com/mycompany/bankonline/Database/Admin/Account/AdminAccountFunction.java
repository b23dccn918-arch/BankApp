package com.mycompany.bankonline.Database.Admin.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Model.Account;

public class AdminAccountFunction {
	
	
	public static Account getAccountById(int id) {
	    String sql = "SELECT * FROM accounts WHERE account_id = ?";
	    try (Connection conn = Connect.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Account acc = new Account();
	            acc.setAccountId(rs.getInt("account_id"));
	            acc.setStatus(rs.getInt("status"));
	            // thêm các field khác nếu cần
	            return acc;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public static boolean banAccountById(int accountId) {
	    String sql = "UPDATE accounts SET status = 0 WHERE account_id = ? AND status = 1";

	    try (Connection conn = Connect.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, accountId);
	        int rowsUpdated = stmt.executeUpdate();

	        return rowsUpdated > 0; // trả về true nếu có dòng bị ảnh hưởng

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	
	public static boolean unBanAccount(int accountId) {
	    String sql = "UPDATE accounts SET status = 1 WHERE account_id = ?";
	    try (Connection conn = Connect.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, accountId);
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public static boolean deleteAccountById(int accountId) {
	    String selectUserId = "SELECT user_id FROM accounts WHERE account_id = ?";
	    String deleteTransactions = "DELETE FROM transactions WHERE from_account_id = ? OR to_account_id = ?";
	    String deleteBills = "DELETE FROM payment_bills WHERE account_id = ?";
	    String deletePasswordResets = "DELETE FROM password_resets WHERE account_id = ?";
	    String deleteAccount = "DELETE FROM accounts WHERE account_id = ?";
	    String deleteUser = "DELETE FROM users WHERE user_id = ?";

	    try (Connection conn = Connect.getConnection()) {
	        conn.setAutoCommit(false);

	        int userId;
	        try (PreparedStatement psSelect = conn.prepareStatement(selectUserId)) {
	            psSelect.setInt(1, accountId);
	            ResultSet rs = psSelect.executeQuery();
	            if (rs.next()) {
	                userId = rs.getInt("user_id");
	            } else {
	                return false; // account không tồn tại
	            }
	        }

	        try (
	            PreparedStatement ps1 = conn.prepareStatement(deleteTransactions);
	            PreparedStatement ps2 = conn.prepareStatement(deleteBills);
	            PreparedStatement ps3 = conn.prepareStatement(deletePasswordResets);
	            PreparedStatement ps4 = conn.prepareStatement(deleteAccount);
	            PreparedStatement ps5 = conn.prepareStatement(deleteUser)
	        ) {
	            // Xóa transactions
	            ps1.setInt(1, accountId);
	            ps1.setInt(2, accountId);
	            ps1.executeUpdate();

	            // Xóa payment_bills
	            ps2.setInt(1, accountId);
	            ps2.executeUpdate();

	            // Xóa password_resets
	            ps3.setInt(1, accountId);
	            ps3.executeUpdate();

	            // Xóa account
	            ps4.setInt(1, accountId);
	            ps4.executeUpdate();

	            // Xóa user
	            ps5.setInt(1, userId);
	            ps5.executeUpdate();

	            conn.commit();
	            return true;
	        } catch (SQLException e) {
	            conn.rollback();
	            e.printStackTrace();
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}





}

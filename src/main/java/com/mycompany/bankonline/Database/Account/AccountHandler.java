package com.mycompany.bankonline.Database.Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.bankonline.Database.Connect;

public class AccountHandler {
    public double getBalanceByAccountId(int accountId) {
        String sql = "SELECT balance FROM accounts WHERE account_id = ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            } else {
                System.err.println("Không tìm thấy tài khoản");
                return -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getEmailByAccountId(int accountId) {
        String sql = "SELECT u.email FROM users u "
                   + "JOIN accounts a ON u.user_id = a.user_id "
                   + "WHERE a.account_id = ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("email");
            } else {
                System.err.println("Không tìm thấy tài khoản");
                return "-1";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "-1";
        }
    }

    public boolean updatePinByAccountId(int accountId, String newPin) {
        String sql = "UPDATE accounts SET pin = ? WHERE account_id = ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPin);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getAccountNumberByAccountId(int accountId) {
        String sql = "SELECT account_number FROM accounts WHERE account_id = ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("account_number");
            } else {
                System.err.println("Không tìm thấy tài khoản");
                return "-1";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "-1";
        }
    }

    public String getPinByAccountId(int accountId) {
        String sql = "SELECT pin FROM accounts WHERE account_id = ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("pin");
            } else {
                return "-1";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "-1";
        }
    }
    
}

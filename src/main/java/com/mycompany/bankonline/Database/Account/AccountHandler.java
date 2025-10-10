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
    
}

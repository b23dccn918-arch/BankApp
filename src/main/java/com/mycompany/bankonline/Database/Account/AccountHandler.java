package com.mycompany.bankonline.Database.Account;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Model.Account;

public class AccountHandler {

    public static boolean withdrawMoney(int accountId, double amount, Timestamp timestamp) {
        String sqlUpdateBalance = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
        String sqlInsertTransaction = 
            "INSERT INTO transactions (from_account_id, to_account_id, bill_id, type, amount, description, created_at) " +
            "VALUES (?, NULL, NULL, 'withdraw', ?, 'Rút tiền từ tài khoản', ?)";

        try (Connection conn = Connect.getConnection();
            PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdateBalance);
            PreparedStatement stmtInsert = conn.prepareStatement(sqlInsertTransaction)) {

            conn.setAutoCommit(false);

            stmtUpdate.setDouble(1, amount);
            stmtUpdate.setInt(2, accountId);
            stmtUpdate.executeUpdate();

            stmtInsert.setInt(1, accountId);
            stmtInsert.setDouble(2, amount);
            stmtInsert.setTimestamp(3, timestamp);
            stmtInsert.executeUpdate();

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean depositMoney(int accountId, double amount, Timestamp timestamp) {
        String sqlUpdateBalance = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        String sqlInsertTransaction = 
            "INSERT INTO transactions (from_account_id, to_account_id, bill_id, type, amount, description, created_at) " +
            "VALUES (?, NULL, NULL, 'deposit', ?, 'Nạp tiền vào tài khoản', ?)";

        try (Connection conn = Connect.getConnection();
            PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdateBalance);
            PreparedStatement stmtInsert = conn.prepareStatement(sqlInsertTransaction)) {

            conn.setAutoCommit(false);

            stmtUpdate.setDouble(1, amount);
            stmtUpdate.setInt(2, accountId);
            stmtUpdate.executeUpdate();

            stmtInsert.setInt(1, accountId);
            stmtInsert.setDouble(2, amount);
            stmtInsert.setTimestamp(3, timestamp);
            stmtInsert.executeUpdate();

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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


    public Account findAccountByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToAccount(rs);
            }
            else{
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Account findAccountByAccountId(int accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToAccount(rs);
            }
            else{
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        return new Account.Builder()
                .setAccountId(rs.getInt("account_id"))
                .setUsername(rs.getString("phone"))
                .setPassword(rs.getString("password"))
                .setUserId(rs.getInt("user_id"))
                .setAccountNumber(rs.getString("account_number"))
                .setBalance(rs.getLong("balance"))
                .setPin(rs.getString("pin"))
                .setStatus(rs.getInt("status"))
                .setCreatedAt(rs.getTimestamp("created_at"))
                .build();
    }

    
}

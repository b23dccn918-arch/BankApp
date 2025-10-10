package com.mycompany.bankonline.Database.Transfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.bankonline.Database.Connect;

public class TransferHandler {

    public String transferMoney(int senderAccountId, String recipientAccountNumber, double amount, String description) {
        try (Connection conn = Connect.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 🔹 Lấy thông tin người gửi
            String sqlSender = "SELECT account_id, balance FROM accounts WHERE account_id = ?";
            PreparedStatement psSender = conn.prepareStatement(sqlSender);
            psSender.setInt(1, senderAccountId);
            ResultSet rsSender = psSender.executeQuery();

            if (!rsSender.next()) {
                return "Không tìm thấy tài khoản người gửi.";
            }

            double senderBalance = rsSender.getDouble("balance");

            // Lấy thông tin người nhận
            String sqlRecipient = "SELECT account_id, balance FROM accounts WHERE account_number = ?";
            PreparedStatement psRecipient = conn.prepareStatement(sqlRecipient);
            psRecipient.setString(1, recipientAccountNumber);
            ResultSet rsRecipient = psRecipient.executeQuery();

            if (!rsRecipient.next()) {
                return "Tài khoản người nhận không tồn tại.";
            }

            int recipientAccountId = rsRecipient.getInt("account_id");
            double recipientBalance = rsRecipient.getDouble("balance");

            //  Kiểm tra số dư
            if (senderBalance < amount) {
                return "Số dư không đủ để chuyển tiền.";
            }

            // Cập nhật số dư người gửi
            PreparedStatement psUpdateSender = conn.prepareStatement(
                "UPDATE accounts SET balance = ? WHERE account_id = ?");
            psUpdateSender.setDouble(1, senderBalance - amount);
            psUpdateSender.setInt(2, senderAccountId);
            psUpdateSender.executeUpdate();

            // Cập nhật số dư người nhận
            PreparedStatement psUpdateRecipient = conn.prepareStatement(
                "UPDATE accounts SET balance = ? WHERE account_id = ?");
            psUpdateRecipient.setDouble(1, recipientBalance + amount);
            psUpdateRecipient.setInt(2, recipientAccountId);
            psUpdateRecipient.executeUpdate();

            // Ghi log giao dịch
            PreparedStatement psLog = conn.prepareStatement(
                "INSERT INTO transactions (from_account_id, to_account_id, type, amount, description, created_at) " +
                "VALUES (?, ?, 'transfer', ?, ?, NOW())");
            psLog.setInt(1, senderAccountId);
            psLog.setInt(2, recipientAccountId);
            psLog.setDouble(3, amount);
            psLog.setString(4, description);
            psLog.executeUpdate();

            conn.commit(); //Hoàn tất giao dịch

            return "Success";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi chuyển tiền: " + e.getMessage();
        }
    }
}


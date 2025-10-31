package com.mycompany.bankonline.Database.Transfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Database.Account.AccountHandler;
import com.mycompany.bankonline.Model.Account;

public class TransferHandler {
    private final AccountHandler accountHandler = new AccountHandler();
    public Boolean transferMoney(int senderAccountId, String recipientAccountNumber, double amount, String description) {
        Account senderAccount = accountHandler.findAccountByAccountId(senderAccountId);
        String senderAccountNumber = senderAccount.getAccountNumber();
        if(senderAccountNumber.equals(recipientAccountNumber)){
            return false;
        }

        try (Connection conn = Connect.getConnection()) {
            conn.setAutoCommit(false);
            

            String sqlSender = "SELECT account_id, balance FROM accounts WHERE account_id = ?";
            PreparedStatement psSender = conn.prepareStatement(sqlSender);
            psSender.setInt(1, senderAccountId);
            ResultSet rsSender = psSender.executeQuery();

            if (!rsSender.next()) {
                return false;
            }

            double senderBalance = rsSender.getDouble("balance");


            String sqlRecipient = "SELECT account_id, balance FROM accounts WHERE account_number = ?";
            PreparedStatement psRecipient = conn.prepareStatement(sqlRecipient);
            psRecipient.setString(1, recipientAccountNumber);
            ResultSet rsRecipient = psRecipient.executeQuery();

            if (!rsRecipient.next()) {
                return false;
            }

            int recipientAccountId = rsRecipient.getInt("account_id");
            double recipientBalance = rsRecipient.getDouble("balance");


            if (senderBalance < amount) {
                return false;
            }


            PreparedStatement psUpdateSender = conn.prepareStatement(
                "UPDATE accounts SET balance = ? WHERE account_id = ?");
            psUpdateSender.setDouble(1, senderBalance - amount);
            psUpdateSender.setInt(2, senderAccountId);
            psUpdateSender.executeUpdate();


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

            conn.commit();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


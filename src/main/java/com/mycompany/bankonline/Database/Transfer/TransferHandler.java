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

        if(senderAccount.getBalance() < amount){
            return false;
        }

        try {
            Connection conn = Connect.getConnection();
            conn.setAutoCommit(false);
            

            Account repipientAccount = accountHandler.findAccountByAccountNumber(recipientAccountNumber);

            if(recipientAccountNumber == null){
                return false;
            }

            PreparedStatement psUpdateSender = conn.prepareStatement(
                "UPDATE accounts SET balance = ? WHERE account_id = ?");
            psUpdateSender.setDouble(1, senderAccount.getBalance() - amount);
            psUpdateSender.setInt(2, senderAccountId);
            psUpdateSender.executeUpdate();


            PreparedStatement psUpdateRecipient = conn.prepareStatement(
                "UPDATE accounts SET balance = ? WHERE account_id = ?");
            psUpdateRecipient.setDouble(1, repipientAccount.getBalance() + amount);
            psUpdateRecipient.setInt(2, repipientAccount.getAccountId());
            psUpdateRecipient.executeUpdate();

            PreparedStatement psLog = conn.prepareStatement(
                "INSERT INTO transactions (from_account_id, to_account_id, type, amount, description, created_at) " +
                "VALUES (?, ?, 'transfer', ?, ?, NOW())");
            psLog.setInt(1, senderAccountId);
            psLog.setInt(2, repipientAccount.getAccountId());
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


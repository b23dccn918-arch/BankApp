package com.mycompany.bankonline.Database.Transaction;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Database.Account.AccountHandler;
import com.mycompany.bankonline.Database.Payment.PaymentHandler;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Model.Bill;
import com.mycompany.bankonline.Model.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionHandler {

    private final AccountHandler accountHandler = new AccountHandler();

    public List<Transaction> getTransactionsByAccountId(int accountId) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT " +
                "t.transaction_id, " +
                "a1.account_number AS from_acc, " +
                "a2.account_number AS to_acc, " +
                "t.type, " +
                "t.amount, " +
                "t.description, " +
                "t.created_at " +
                "FROM transactions t " +
                "LEFT JOIN accounts a1 ON t.from_account_id = a1.account_id " +
                "LEFT JOIN accounts a2 ON t.to_account_id = a2.account_id " +
                "WHERE t.from_account_id = ? OR t.to_account_id = ? " +
                "ORDER BY t.created_at DESC";

        try (Connection conn = Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ps.setInt(2, accountId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction tx = new Transaction(
                    rs.getLong("transaction_id"),
                    rs.getString("from_acc"),
                    rs.getString("to_acc"),
                    rs.getString("type"),
                    rs.getBigDecimal("amount"),
                    rs.getString("description"),
                    rs.getTimestamp("created_at"),
                    "Hoàn tất"
                );
                list.add(tx);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Transaction> filterTransactionsByAccountId(LocalDate from, LocalDate to,int accountId) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT t.transaction_id, " +
                     "a1.account_number AS from_acc, a2.account_number AS to_acc, " +
                     "t.type, t.amount, t.description, t.created_at " +
                     "FROM transactions t " +
                     "LEFT JOIN accounts a1 ON t.from_account_id = a1.account_id " +
                     "LEFT JOIN accounts a2 ON t.to_account_id = a2.account_id " +
                     "WHERE DATE(t.created_at) BETWEEN ? AND ? AND (t.from_account_id = ? OR t.to_account_id = ?) " +
                     "ORDER BY t.created_at DESC";

        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            ps.setInt(3, accountId);
            ps.setInt(4, accountId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction tx = new Transaction(
                    rs.getLong("transaction_id"),
                    rs.getString("from_acc"),
                    rs.getString("to_acc"),
                    rs.getString("type"),
                    rs.getBigDecimal("amount"),
                    rs.getString("description"),
                    rs.getTimestamp("created_at"),
                    "Hoàn tất"
                );
                list.add(tx);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<String> getRecentTransactions(int accountId, int limit) {
        List<String> transactions = new ArrayList<>();

        String sql = "SELECT " +
                "type, amount, from_account_id, to_account_id,bill_id, created_at " +
                "FROM transactions " +
                "WHERE from_account_id = ? OR to_account_id = ? " +
                "ORDER BY created_at DESC " +
                "LIMIT ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ps.setInt(2, accountId);
            ps.setInt(3, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                int fromId = rs.getInt("from_account_id");
                int toId = rs.getInt("to_account_id");
                int billId = rs.getInt("bill_id");  
                Timestamp time = rs.getTimestamp("created_at");
                String formatted = time.toLocalDateTime().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                );


                String message;

                if (type.equalsIgnoreCase("transfer") && fromId == accountId) {
                    Account toAccount = accountHandler.findAccountByAccountId(toId);
                    String toAccountNumber = toAccount != null ? toAccount.getAccountNumber() : "Unknown";
                    message = String.format("You have transferred %.0f VND to account %s -- %s", amount, toAccountNumber, formatted.toString());
                }
                else if (type.equalsIgnoreCase("transfer") && toId == accountId) {
                    Account fromAccount = accountHandler.findAccountByAccountId(fromId);
                    String fromAccountNumber = fromAccount != null ? fromAccount.getAccountNumber() : "Unknown";
                    message = String.format("You have received %.0f VND from account #%s -- %s", amount, fromAccountNumber, formatted.toString());
                }
                else if (type.equalsIgnoreCase("deposit"))
                    message = String.format("Deposit of %.0f VND successful -- %s.", amount, formatted.toString());
                else if (type.equalsIgnoreCase("withdraw"))
                    message = String.format("Withdrawal of %.0f VND successful -- %s.", amount, formatted.toString());
                else if (type.equalsIgnoreCase("payment")) {
                    Bill bill = PaymentHandler.getBillByBillId(billId);
                    message = String.format("Payment of %.0f VND for bill #%d (Type: %s) -- %s",
                            amount, billId, bill.getBillType(), formatted.toString());
                }
                else
                    message = String.format("Transaction of %.0f VND (%s).", amount, type);
                transactions.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}

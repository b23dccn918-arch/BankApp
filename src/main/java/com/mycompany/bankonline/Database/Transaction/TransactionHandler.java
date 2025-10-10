package com.mycompany.bankonline.Database.Transaction;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Model.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionHandler {

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
                    rs.getDouble("amount"),
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
                    rs.getDouble("amount"),
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
}

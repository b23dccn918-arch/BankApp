package com.mycompany.bankonline.Database.Payment;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Model.Bill;

public class PaymentHandler {


    public static ObservableList<Bill> getBillsByAccountId(int accountId) {
        ObservableList<Bill> bills = FXCollections.observableArrayList();

        String sql = "SELECT * FROM payment_bills WHERE account_id = ?";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                long billId = rs.getLong("bill_id");
                String billType = rs.getString("bill_type");
                double amount = rs.getDouble("amount");
                String status = rs.getString("status");
                String due = rs.getTimestamp("due_date").toLocalDateTime().format(fmt);
                String created = rs.getTimestamp("created_at").toLocalDateTime().format(fmt);

                bills.add(new Bill(billId, billType, amount, status, due, created));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bills;
    }

    // Lấy danh sách bill_id đang chờ thanh toán
    public static ObservableList<String> getUnpaidBillIds(int accountId) {
        ObservableList<String> pendingIds = FXCollections.observableArrayList();

        String sql = "SELECT bill_id FROM payment_bills WHERE account_id = ? AND status = 'unpaid'";

        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pendingIds.add(String.valueOf(rs.getLong("bill_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pendingIds;
    }

    public static ObservableList<String> getPaidBillIds(int accountId) {
        ObservableList<String> pendingIds = FXCollections.observableArrayList();

        String sql = "SELECT bill_id FROM payment_bills WHERE account_id = ? AND status = 'unpaid'";

        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pendingIds.add(String.valueOf(rs.getLong("bill_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pendingIds;
    }

    // Thực hiện thanh toán hóa đơn
    public static int payBill(int accountId, long billId) {
        Connection conn = null;

        try {
            conn = Connect.getConnection();
            conn.setAutoCommit(false);

            // Lấy thông tin hóa đơn
            String billQuery = "SELECT amount, status FROM payment_bills WHERE bill_id = ?";
            PreparedStatement billStmt = conn.prepareStatement(billQuery);
            billStmt.setLong(1, billId);
            ResultSet rs = billStmt.executeQuery();

            if (!rs.next()) {
                return 3; // Không tìm thấy hóa đơn
            }

            double amount = rs.getDouble("amount");
            String status = rs.getString("status");

            if ("paid".equalsIgnoreCase(status)) {
                return 2; // Hóa đơn đã thanh toán
            }

            // Kiểm tra số dư tài khoản
            String balanceQuery = "SELECT balance FROM accounts WHERE account_id = ?";
            PreparedStatement accStmt = conn.prepareStatement(balanceQuery);
            accStmt.setInt(1, accountId);
            ResultSet accRs = accStmt.executeQuery();

            if (!accRs.next()) {
                return 3; // Không tìm thấy tài khoản
            }

            double balance = accRs.getDouble("balance");

            if (balance < amount) {
                return 1; // Không đủ số dư
            }

            // Trừ tiền và cập nhật hóa đơn
            String updateBalance = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateBalance);
            updateStmt.setDouble(1, amount);
            updateStmt.setInt(2, accountId);
            updateStmt.executeUpdate();

            String updateBill = "UPDATE payment_bills SET status = 'paid', paid_at = NOW() WHERE bill_id = ?";
            PreparedStatement billUpdateStmt = conn.prepareStatement(updateBill);
            billUpdateStmt.setLong(1, billId);
            billUpdateStmt.executeUpdate();

            //Ghi vào transactions
            String insertTrans = "INSERT INTO transactions (from_account_id, type, amount, description, created_at) VALUES (?, 'payment', ?, ?, NOW())";
            PreparedStatement transStmt = conn.prepareStatement(insertTrans);
            transStmt.setInt(1, accountId);
            transStmt.setDouble(2, amount);
            transStmt.setString(3, "Thanh toán hoá đơn #" + billId);
            transStmt.executeUpdate();

            conn.commit();
            return 0; // Thành công

        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ignored) {}
            e.printStackTrace();
            return 99; // Lỗi hệ thống
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }

}

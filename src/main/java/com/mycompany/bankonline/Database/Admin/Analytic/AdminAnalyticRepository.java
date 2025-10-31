package com.mycompany.bankonline.Database.Admin.Analytic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.mycompany.bankonline.Database.Connect;


public class AdminAnalyticRepository {
	private Connection conn = Connect.getConnection();
	
	 // Đếm số lượng tài khoản Active/Inactive
    public Map<String, Integer> getAccountStatusCount() {
        Map<String, Integer> data = new HashMap<>();
        String sql = "SELECT status, COUNT(*) AS total FROM accounts GROUP BY status";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String status = rs.getString("status");
                int count = rs.getInt("total");
                data.put(status, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    
 // Đếm số hóa đơn Paid/Unpaid
    public Map<String, Integer> getBillStatusCount() {
        Map<String, Integer> data = new HashMap<>();
        String sql = "SELECT status, COUNT(*) AS total FROM payemt_bill GROUP BY status";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String status = rs.getString("status");
                int count = rs.getInt("total");
                data.put(status, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    // Tổng giao dịch theo tháng
    public Map<String, Double> getTransactionTotalByMonth() {
        Map<String, Double> data = new HashMap<>();
        String sql = "SELECT MONTH(transaction_date) AS month, SUM(amount) AS total " +
                     "FROM transactions GROUP BY MONTH(transaction_date)";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String month = "Tháng " + rs.getInt("month");
                double total = rs.getDouble("total");
                data.put(month, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    
}

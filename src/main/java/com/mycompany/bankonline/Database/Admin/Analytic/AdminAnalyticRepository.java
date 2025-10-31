package com.mycompany.bankonline.Database.Admin.Analytic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mycompany.bankonline.Database.Connect;



public class AdminAnalyticRepository {

    private final Connection conn;

    public AdminAnalyticRepository() {
        this.conn = Connect.getConnection();
    }

    public Map<String, Integer> getAccountStatusCount() {
        Map<String, Integer> result = new LinkedHashMap<>();
        // Khởi tạo mặc định để bảo đảm có 2 key luôn
        result.put("Active", 0);
        result.put("Inactive", 0);

        String sql = "SELECT status, COUNT(*) AS total FROM accounts GROUP BY status";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int status = rs.getInt("status"); // theo schema status INT DEFAULT 1
                int total = rs.getInt("total");
                if (status == 1) {
                    result.put("Active", total);
                } else {
                    // mọi giá trị khác coi là Inactive (0,2,...)
                    int prev = result.getOrDefault("Inactive", 0);
                    result.put("Inactive", prev + total);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // nếu lỗi thì trả map mặc định với 0
        }
        return result;
    }


  
    public Map<String, Double> getBillAmountByType() {
        Map<String, Double> result = new HashMap<>();

        String sql = "SELECT bill_type, SUM(amount) AS total_amount " +
                     "FROM payment_bills GROUP BY bill_type";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("bill_type");
                double amount = rs.getDouble("total_amount");
                result.put(type, amount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }



    public Map<String, Double> getTransactionTotalByMonth() {
        
        Map<String, Double> result = new LinkedHashMap<>();
        for (int m = 1; m <= 12; m++) {
            result.put("Tháng " + m, 0.0);
        }


        String sql = "SELECT MONTH(created_at) AS month, SUM(amount) AS total " +
                     "FROM transactions " +
                     "WHERE YEAR(created_at) = YEAR(CURRENT_DATE()) " +
                     "GROUP BY MONTH(created_at) " +
                     "ORDER BY MONTH(created_at)";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int month = rs.getInt("month"); // 1..12
                double total = rs.getDouble("total");
                String key = "Tháng " + month;
                if (result.containsKey(key)) {
                    result.put(key, total);
                } else {
                    // phòng trường hợp (không xảy ra) nhưng an toàn
                    result.put(key, total);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}

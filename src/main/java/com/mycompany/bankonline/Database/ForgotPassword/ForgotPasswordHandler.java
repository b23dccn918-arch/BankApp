package com.mycompany.bankonline.Database.ForgotPassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Service.EmailService;

public class ForgotPasswordHandler {
    
    private static final EmailService emailService = new EmailService();
    public static boolean verifyToken(String email, String token) {
        try(Connection conn = Connect.getConnection()){


            String sql = "SELECT pr.expires_at, pr.used FROM password_resets pr "
                       + "JOIN accounts a ON pr.account_id = a.account_id "
                       + "JOIN users u ON a.user_id = u.user_id "
                       + "WHERE u.email = ? AND pr.token = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, token);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Timestamp expiresAt = rs.getTimestamp("expires_at");
                boolean used = rs.getBoolean("used");

                if (used || expiresAt.toLocalDateTime().isBefore(LocalDateTime.now())) {
                    return false; 
                }

                // String update = "UPDATE password_resets SET used = TRUE WHERE token = ?";
                // PreparedStatement ups = conn.prepareStatement(update);
                // ups.setString(1, token);
                // ups.executeUpdate();

                return true; // token hợp lệ
            } else {
                return false; // không tìm thấy token
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendResetToken(String email) {
        try(Connection conn = Connect.getConnection()){
            String sql = "SELECT a.account_id FROM accounts a JOIN users u ON a.user_id = u.user_id WHERE u.email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return false; // không tồn tại email

            int accountId = rs.getInt("account_id");
            String token = String.format("%06d", new Random().nextInt(999999)); // mã 6 chữ số
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
    
            String insert = "INSERT INTO password_resets(account_id, token, expires_at, used) VALUES (?, ?, ?, FALSE)";
            PreparedStatement ins = conn.prepareStatement(insert);
            ins.setInt(1, accountId);
            ins.setString(2, token);
            ins.setTimestamp(3, Timestamp.valueOf(expiresAt));
            ins.executeUpdate();

            // Gửi email
            emailService.sendResetToken(email,token);
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean resetPassword(String email, String token, String newPassword) {
        Connection conn = null;
        try {
            conn = Connect.getConnection();
            conn.setAutoCommit(false);
            String find = "SELECT a.account_id, pr.reset_id FROM password_resets pr "
                    + "JOIN accounts a ON pr.account_id = a.account_id "
                    + "JOIN users u ON a.user_id = u.user_id "
                    + "WHERE u.email = ? AND pr.token = ? AND pr.used = FALSE AND pr.expires_at > NOW()";
            PreparedStatement ps = conn.prepareStatement(find);
            ps.setString(1, email);
            ps.setString(2, token);
            ResultSet rs = ps.executeQuery();
            
            if (!rs.next()) return false;

            int accountId = rs.getInt("account_id");
            long resetId = rs.getLong("reset_id");

            // Cập nhật mật khẩu (mã hóa nếu có)
            String update = "UPDATE accounts SET password = ? WHERE account_id = ?";
            PreparedStatement up = conn.prepareStatement(update);
            up.setString(1, newPassword);
            up.setInt(2, accountId);
            up.executeUpdate();

            // Đánh dấu token đã dùng
            PreparedStatement mark = conn.prepareStatement("UPDATE password_resets SET used = TRUE WHERE reset_id = ?");
            mark.setLong(1, resetId);
            mark.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            return false;
        } finally {
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException ex) {}
        }
    }

}

package com.mycompany.bankonline.Database.ForgotPin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Database.UserInfo.UserInfoHandler;
import com.mycompany.bankonline.Service.EmailService;
import com.mycompany.bankonline.Session.Session;

public class ForgotPinHandler {
    private static final EmailService emailService = new EmailService();
    private static final UserInfoHandler userInfoHandler = new UserInfoHandler();
    public static boolean verifyToken(String email, String token) {
        try(Connection conn = Connect.getConnection()){
            

            String sql = "SELECT pr.expires_at, pr.used FROM pin_resets pr "
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

                return true; 
            } else {
                return false; 
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendResetToken(int accountId) {
        try(Connection conn = Connect.getConnection()){
            // String sql = "SELECT a.account_id FROM accounts a JOIN users u ON a.user_id = u.user_id WHERE u.email = ?";
            // PreparedStatement ps = conn.prepareStatement(sql);
            // ps.setString(1, email);
            // ResultSet rs = ps.executeQuery();

            // if (!rs.next()) return false; 

            // int accountId = rs.getInt("account_id");
            String token = String.format("%06d", new Random().nextInt(999999)); 
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
    
            String insert = "INSERT INTO pin_resets(account_id, token, expires_at, used) VALUES (?, ?, ?, FALSE)";
            PreparedStatement ins = conn.prepareStatement(insert);
            ins.setInt(1, accountId);
            ins.setString(2, token);
            ins.setTimestamp(3, Timestamp.valueOf(expiresAt));
            ins.executeUpdate();
            String email = userInfoHandler.getEmailByUserId(Session.getInstance().getUserId());
            // Gửi email
            emailService.sendPinResetToken(email,token);
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean resetPin(String email, String token, String newPin) {
        String queryFind = "SELECT a.account_id, pr.reset_id\n"
                + "FROM pin_resets pr\n"
                + "JOIN accounts a ON pr.account_id = a.account_id\n"
                + "JOIN users u ON a.user_id = u.user_id\n"
                + "WHERE u.email = ? \n"
                + "AND pr.token = ? \n"
                + "AND pr.used = 0 \n"
                + "AND pr.expires_at > NOW()";

        String queryUpdatePin = "UPDATE accounts SET pin = ? WHERE account_id = ?";
        String queryMarkUsed = "UPDATE pin_resets SET used = 1 WHERE reset_id = ?";

        try (Connection conn = Connect.getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement psFind = conn.prepareStatement(queryFind);
            psFind.setString(1, email);
            psFind.setString(2, token);
            ResultSet rs = psFind.executeQuery();

            if (!rs.next()) {
                conn.rollback();
                return false; 
            }

            int accountId = rs.getInt("account_id");
            long resetId = rs.getLong("reset_id");

            PreparedStatement psUpdate = conn.prepareStatement(queryUpdatePin);
            psUpdate.setString(1, newPin);
            psUpdate.setInt(2, accountId);
            psUpdate.executeUpdate();

            PreparedStatement psMark = conn.prepareStatement(queryMarkUsed);
            psMark.setLong(1, resetId);
            psMark.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

}

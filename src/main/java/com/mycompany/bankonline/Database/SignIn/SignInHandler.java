package com.mycompany.bankonline.Database.SignIn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.bankonline.Database.Connect;


import com.mycompany.bankonline.Session.Session;


public class SignInHandler {
    public static boolean checkSignIn(String username, String password) {
        Connection con = Connect.getConnection();
        String sql = "SELECT * FROM accounts WHERE phone = ? AND password = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            // Nếu có ít nhất một dòng kết quả -> tài khoản tồn tại
            if (rs.next()) {
                // luu thong tin vao session;
                int userId = rs.getInt("user_id"); 
                int accountId = rs.getInt("account_id");
                Session.getInstance().setSession(userId, accountId);
                System.out.println(Session.getInstance());

                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false; // Không tìm thấy tài khoản
    }
}


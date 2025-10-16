package com.mycompany.bankonline.Database.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.bankonline.Database.Connect;


public class AdminSignIn {
	public static boolean checkSignIn(String username, String password) {
        Connection con = Connect.getConnection();
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false; // Không tìm thấy tài khoản
    }
}

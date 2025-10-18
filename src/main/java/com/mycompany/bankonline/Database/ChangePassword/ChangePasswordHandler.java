package com.mycompany.bankonline.Database.ChangePassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mycompany.bankonline.Database.Connect;

public class ChangePasswordHandler {
    

    public boolean checkCurrentPassword(String phoneNumber, String currentPassword) {
        String sql = "SELECT * FROM accounts ac "
                   + "WHERE ac.phone = ? AND ac.password = ?";
        try(Connection conn = Connect.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, phoneNumber);
            ps.setString(2, currentPassword);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true; 
    }

    public boolean changePasswordInDatabase(String phoneNumber, String currentPassword, String newPassword) {
        String sql = "UPDATE accounts ac "
                   + "SET ac.password = ? "
                   + "WHERE ac.phone = ? AND ac.password = ?";
        try(Connection conn = Connect.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, phoneNumber);
            ps.setString(3, currentPassword);
            int update = ps.executeUpdate();
            if(update > 0){
                return true;
            }

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}

package com.mycompany.bankonline.Database.Complaint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Model.Complaint;


public class ComplaintHandler {
    public static boolean SendComplaint(int accountId,String subject, String content){
        String sql = "INSERT INTO complaints(account_id,subject,content,status,created_at) "
                    + "VALUES (?,?,?,'pending',?)";
        Connection con = Connect.getConnection();
        try{
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, accountId);
            stmt.setString(2, subject);
            stmt.setString(3, content);
            stmt.setTimestamp(4,  Timestamp.valueOf(LocalDateTime.now()));

            int result = stmt.executeUpdate();
            if(result > 0){
                return true;
            }
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static List<Complaint> getAllComplaintsByAccountId(int accountId){
        List<Complaint> list = new ArrayList<>();
        String sql = "SELECT complaint_id,account_id,subject,content,status,admin_note,created_at,updated_at FROM complaints WHERE account_id = ? ORDER BY created_at DESC";
        try (Connection conn = Connect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Complaint c = new Complaint.Builder()
                                        .complaintId(rs.getInt("complaint_id"))
                                        .accountId(rs.getInt("account_id"))
                                        .subject(rs.getString("subject"))
                                        .content(rs.getString("content"))
                                        .status(rs.getString("status"))
                                        .adminNote(rs.getString("admin_note"))
                                        .createdAt(rs.getTimestamp("created_at"))
                                        .updatedAt(rs.getTimestamp("updated_at"))
                                        .build();
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Complaint> getAllComplaints(){
        List<Complaint> list = new ArrayList<>();
        String sql = "SELECT complaint_id,account_id,subject,content,status,admin_note,created_at,updated_at FROM complaints ORDER BY created_at DESC";
        try (Connection conn = Connect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Complaint c = new Complaint.Builder()
                                        .complaintId(rs.getInt("complaint_id"))
                                        .accountId(rs.getInt("account_id"))
                                        .subject(rs.getString("subject"))
                                        .content(rs.getString("content"))
                                        .status(rs.getString("status"))
                                        .adminNote(rs.getString("admin_note"))
                                        .createdAt(rs.getTimestamp("created_at"))
                                        .updatedAt(rs.getTimestamp("updated_at"))
                                        .build();
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Boolean updateStatusByComplaintId(int complaintId,String status){
        String sql = "UPDATE complaints SET status = ? WHERE complaint_id = ?";

        try(Connection conn = Connect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, status);
                stmt.setInt(2, complaintId);
            int rows = stmt.executeUpdate();
            if(rows > 0){
                return true;
            }
            return false;
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public static Boolean updateAdminNoteByComplaintId(int complaintId,String adminNote){
        String sql = "UPDATE complaints SET admin_note = ? WHERE complaint_id = ?";

        try(Connection conn = Connect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, adminNote);
                stmt.setInt(2, complaintId);
            int rows = stmt.executeUpdate();
            if(rows > 0){
                return true;
            }
            return false;
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
}

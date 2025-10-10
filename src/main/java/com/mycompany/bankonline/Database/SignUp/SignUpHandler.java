package com.mycompany.bankonline.Database.SignUp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mycompany.bankonline.Database.Connect;

public class SignUpHandler {
	public static boolean isIdentificationExist(String identification) {
	    String query = "SELECT 1 FROM users WHERE citizen_identifier = ?";
	    try (Connection conn = Connect.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        
	        stmt.setString(1, identification);
	        ResultSet rs = stmt.executeQuery();
	        return rs.next(); // true nếu có kết quả -> đã tồn tại
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; // nếu lỗi DB thì tạm coi như không tồn tại
	    }
	}

}

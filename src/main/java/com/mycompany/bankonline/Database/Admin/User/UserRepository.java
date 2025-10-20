package com.mycompany.bankonline.Database.Admin.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserRepository {
	public static ObservableList<User> getAllUsers(){
		Connection con = Connect.getConnection();
		String sql = "select * from users";
		ObservableList<User> users = FXCollections.observableArrayList();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int userId = rs.getInt("user_id");
				String fullName = rs.getString("full_name");
				String citizenIdentifier = rs.getString("citizen_identifier");
				String job = rs.getString("job");
				String gender = rs.getString("gender");
				Date dateBirth = rs.getDate("dateBirth");
				String address = rs.getString("address");
				String email = rs.getString("email");
				Timestamp createdAt = rs.getTimestamp("created_at");
				users.add(new User(userId, fullName, citizenIdentifier, job, gender, dateBirth, address, email, createdAt));
			}
			ps.close();
	        rs.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return  users;
		
	}
}

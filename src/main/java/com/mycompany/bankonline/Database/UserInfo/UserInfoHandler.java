package com.mycompany.bankonline.Database.UserInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Model.User;
import com.mycompany.bankonline.Session.Session;

public class UserInfoHandler {

    public User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = Connect.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("citizen_identifier"),
                    rs.getString("job"),
                    rs.getString("gender"),
                    rs.getDate("dateBirth"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getTimestamp("created_at")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

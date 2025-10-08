package com.mycompany.bankonline.Database.UserInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Session.Session;

public class UserInfoHandler {
    private void loadUserInfo() {
        // Connection con = Connect.getConnection();
        // int userId = Session.getInstance().getUserId();

        // String query = "SELECT full_name, gender, dateBirth, citizen_identifier, job, address, email, created_at "
        //              + "FROM users WHERE user_id = ?";

        // try{
        //      PreparedStatement stmt = con.prepareStatement(query);

        //     stmt.setInt(1, userId);
        //     ResultSet rs = stmt.executeQuery();

        //     if (rs.next()) {
        //         fullNameLabel.setText(rs.getString("full_name"));
        //         genderLabel.setText(rs.getString("gender"));
                
        //         // Định dạng ngày sinh đẹp hơn
        //         Timestamp dateBirth = rs.getTimestamp("dateBirth");
        //         if (dateBirth != null) {
        //             dateBirthLabel.setText(dateBirth.toLocalDateTime()
        //                     .toLocalDate()
        //                     .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        //         } else {
        //             dateBirthLabel.setText("-");
        //         }

        //         citizenIdLabel.setText(rs.getString("citizen_identifier"));
        //         jobLabel.setText(rs.getString("job"));
        //         addressLabel.setText(rs.getString("address"));
        //         emailLabel.setText(rs.getString("email"));

        //         Timestamp createdAt = rs.getTimestamp("created_at");
        //         if (createdAt != null) {
        //             createdAtLabel.setText(createdAt.toLocalDateTime()
        //                     .toLocalDate()
        //                     .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        //         } else {
        //             createdAtLabel.setText("-");
        //         }

        //         statusLabel.setText("Active");
        //         statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        //     } else {
        //         fullNameLabel.setText("Không tìm thấy người dùng!");
        //     }

        // } catch (SQLException e) {
        //     e.printStackTrace();
        //     fullNameLabel.setText("Lỗi tải dữ liệu");
        // }
    }
}

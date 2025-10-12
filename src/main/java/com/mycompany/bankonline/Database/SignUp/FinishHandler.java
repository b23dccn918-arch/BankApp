package com.mycompany.bankonline.Database.SignUp;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mycompany.bankonline.Database.Connect;

public class FinishHandler {
	public static boolean isPhoneExist(String identification) {
		String query = "SELECT 1 FROM accounts WHERE phone = ?";
		try (Connection conn = Connect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, identification);
			ResultSet rs = stmt.executeQuery();
			return rs.next(); // true nếu có kết quả -> đã tồn tại

		} catch (Exception e) {
			e.printStackTrace();
			return false; // nếu lỗi DB thì tạm coi như không tồn tại
		}
	}

	public static boolean insertUser(String fullName, String citizenId, String job, String gender, LocalDate dateBirth,
			String address, String email) {
		String sql = "INSERT INTO users (full_name, citizen_identifier, job, gender, dateBirth, address, email, created_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = Connect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, fullName);
			ps.setString(2, citizenId);
			ps.setString(3, job);
			ps.setString(4, gender);
			ps.setDate(5, Date.valueOf(dateBirth));
			ps.setString(6, address);
			ps.setString(7, email);
			ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now())); // created_at = thời gian hiện tại

			int rows = ps.executeUpdate();
			return rows > 0; // trả về true nếu thêm thành công

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public static int getUserIdByCitizenId(String citizenId) {
		String sql = "SELECT user_id FROM users WHERE citizen_identifier = ?";
		try (Connection conn = Connect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, citizenId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("user_id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // Không tìm thấy
	}

	public static boolean insertAccount(String accountNumber, int userId, String phone, String password, double balance,
			String pin) {
		String sql = "INSERT INTO accounts (account_number, user_id, phone, password, balance, pin, created_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = Connect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, accountNumber);
			ps.setInt(2, userId);
			ps.setString(3, phone);
			ps.setString(4, password);
			ps.setDouble(5, balance);
			ps.setString(6, pin);
			ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));

			int rows = ps.executeUpdate();
			return rows > 0; // trả về true nếu thêm thành công

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}

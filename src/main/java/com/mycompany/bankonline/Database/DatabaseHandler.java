package com.mycompany.bankonline.Database;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.bankonline.Model.Card;
import com.mycompany.bankonline.Model.Transaction;
import com.mycompany.bankonline.Model.User;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/"
                + dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);


        return dbConnection;
    }


    public void insertUser(User u) {
        String sql = "INSERT INTO users(full_name, phone, citizen_identifier, job, address, email, password_hash, account_type, balance, created_at, status) "
                   + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getFullName());
            ps.setString(2, u.getPhone());
            ps.setString(3, u.getCitizenIdentifier());
            ps.setString(4, u.getJob());
            ps.setString(5, u.getAddress());
            ps.setString(6, u.getEmail());
            ps.setString(7, u.getPasswordHash());
            ps.setString(8, u.getAccountType());
            ps.setBigDecimal(9, u.getBalance());
            ps.setTimestamp(10, Timestamp.valueOf(u.getCreatedAt()));
            ps.setString(11, u.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy user theo phone + password (đăng nhập)
    public User getUserByLogin(String phone, String password) {
        String sql = "SELECT * FROM users WHERE phone=? AND password_hash=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setFullName(rs.getString("full_name"));
                u.setPhone(rs.getString("phone"));
                u.setEmail(rs.getString("email"));
                u.setAccountType(rs.getString("account_type"));
                u.setBalance(rs.getBigDecimal("balance"));
                u.setStatus(rs.getString("status"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật số dư
    public void updateBalance(int userId, double newBalance) {
        String sql = "UPDATE users SET balance=? WHERE user_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xoá user
    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách tất cả users
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setFullName(rs.getString("full_name"));
                u.setPhone(rs.getString("phone"));
                u.setEmail(rs.getString("email"));
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public void insertCard(Card c) {
        String sql = "INSERT INTO cards(user_id, card_number, card_type, expired_date, cvv, status) VALUES(?,?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getUserId());
            ps.setString(2, c.getCardNumber());
            ps.setString(3, c.getCardType());
            ps.setDate(4, Date.valueOf(c.getExpiredDate()));
            ps.setString(5, c.getCvv());
            ps.setString(6, c.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách thẻ theo userId
    public List<Card> getCardsByUser(int userId) {
        List<Card> list = new ArrayList<>();
        String sql = "SELECT * FROM cards WHERE user_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Card c = new Card();
                c.setCardId(rs.getInt("card_id"));
                c.setUserId(rs.getInt("user_id"));
                c.setCardNumber(rs.getString("card_number"));
                c.setCardType(rs.getString("card_type"));
                c.setStatus(rs.getString("status"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Xoá thẻ
    public void deleteCard(int cardId) {
        String sql = "DELETE FROM cards WHERE card_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cardId);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertTransaction(Transaction t) {
        String sql = "INSERT INTO transactions(from_user_id, to_user_id, type, amount, description, created_at, status) "
                   + "VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getFromUserId());
            if (t.getToUserId() != null)
                ps.setInt(2, t.getToUserId());
            else
                ps.setNull(2, Types.INTEGER);
            ps.setString(3, t.getType());
            ps.setBigDecimal(4, t.getAmount());
            ps.setString(5, t.getDescription());
            ps.setTimestamp(6, Timestamp.valueOf(t.getCreatedAt()));
            ps.setString(7, t.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy tất cả giao dịch của 1 user (gửi hoặc nhận)
    public List<Transaction> getTransactionsByUser(int userId) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE from_user_id=? OR to_user_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setTransactionId(rs.getLong("transaction_id"));
                t.setFromUserId(rs.getInt("from_user_id"));
                t.setToUserId((Integer) rs.getObject("to_user_id"));
                t.setType(rs.getString("type"));
                t.setAmount(rs.getBigDecimal("amount"));
                t.setDescription(rs.getString("description"));
                t.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                t.setStatus(rs.getString("status"));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

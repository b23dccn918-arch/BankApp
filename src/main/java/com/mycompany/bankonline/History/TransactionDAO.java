// package com.mycompany.bankonline.History;


// import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;

// public class TransactionDAO {
//     private final String jdbcURL = "jdbc:mysql://localhost:3306/bankdb"; // đổi tên db
//     private final String jdbcUser = "root"; // user db
//     private final String jdbcPassword = "password"; // pass db

//     public TransactionDAO() {
//         try {
//             Class.forName("com.mysql.cj.jdbc.Driver"); // driver MySQL 8
//         } catch (ClassNotFoundException e) {
//             e.printStackTrace();
//         }
//     }

//     private Connection getConnection() throws SQLException {
//         return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
//     }

//     // Lấy tất cả giao dịch của 1 user
//     public List<Transaction> getTransactionsByUserId(int userId) {
//         List<Transaction> transactions = new ArrayList<>();
//         String sql = "SELECT * FROM transactions WHERE from_user_id = ? OR to_user_id = ? ORDER BY created_at DESC";

//         try (Connection conn = getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {

//             stmt.setInt(1, userId);
//             stmt.setInt(2, userId);

//             try (ResultSet rs = stmt.executeQuery()) {
//                 while (rs.next()) {
//                     TransactionModel transaction = new Transaction(
//                         rs.getLong("transaction_id"),
//                         rs.getInt("from_user_id"),
//                         rs.getObject("to_user_id") != null ? rs.getInt("to_user_id") : null,
//                         rs.getString("type"),
//                         rs.getBigDecimal("amount"),
//                         rs.getString("description"),
//                         rs.getTimestamp("created_at").toLocalDateTime(),
//                         rs.getString("status")
//                     );
//                     transactions.add(transaction);
//                 }
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return transactions;
//     }

//     // Thêm giao dịch mới
//     public boolean insertTransaction(Transaction transaction) {
//         String sql = "INSERT INTO transactions (from_user_id, to_user_id, type, amount, description, created_at, status) " +
//                      "VALUES (?, ?, ?, ?, ?, ?, ?)";

//         try (Connection conn = getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {

//             stmt.setInt(1, transaction.getFromUserId());
//             if (transaction.getToUserId() != null) {
//                 stmt.setInt(2, transaction.getToUserId());
//             } else {
//                 stmt.setNull(2, Types.INTEGER);
//             }
//             stmt.setString(3, transaction.getType());
//             stmt.setBigDecimal(4, transaction.getAmount());
//             stmt.setString(5, transaction.getDescription());
//             stmt.setTimestamp(6, Timestamp.valueOf(transaction.getCreatedAt()));
//             stmt.setString(7, transaction.getStatus());

//             return stmt.executeUpdate() > 0;

//         } catch (SQLException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }
// }

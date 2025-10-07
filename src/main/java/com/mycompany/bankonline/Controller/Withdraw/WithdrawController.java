package com.mycompany.bankonline.Controller.Withdraw;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import java.time.LocalDateTime;

public class WithdrawController {

    @FXML
    private TextField phoneField;

    @FXML
    private TextField amountField;

    @FXML
    private PasswordField pinField;

    @FXML
    private Label messageLabel;

    private final String URL = "jdbc:mysql://localhost:3306/bankdb";
    private final String USER = "root";
    private final String PASSWORD = "your_password";

    @FXML
    public void handleWithdraw() {
        String phone = phoneField.getText().trim();
        String pin = pinField.getText().trim();
        double amount;

        try {
            amount = Double.parseDouble(amountField.getText().trim());
        } catch (NumberFormatException e) {
            messageLabel.setText("Số tiền không hợp lệ!");
            return;
        }

        if (phone.isEmpty() || pin.isEmpty()) {
            messageLabel.setText("Vui lòng nhập đủ thông tin!");
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // 1️⃣ Lấy thông tin tài khoản
            String sql = "SELECT account_id, balance, pin FROM accounts WHERE phone = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                messageLabel.setText("Tài khoản không tồn tại!");
                return;
            }

            int accountId = rs.getInt("account_id");
            double balance = rs.getDouble("balance");
            String dbPin = rs.getString("pin");

            // 2️⃣ Kiểm tra mã PIN
            if (!dbPin.equals(pin)) {
                messageLabel.setText("Mã PIN không đúng!");
                return;
            }

            // 3️⃣ Kiểm tra số dư
            if (balance < amount) {
                messageLabel.setText("Số dư không đủ!");
                return;
            }

            conn.setAutoCommit(false); // bắt đầu transaction

            try {
                // 4️⃣ Cập nhật số dư
                PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE accounts SET balance = balance - ? WHERE account_id = ?");
                updateStmt.setDouble(1, amount);
                updateStmt.setInt(2, accountId);
                updateStmt.executeUpdate();

                // 5️⃣ Ghi vào bảng transactions
                PreparedStatement insertTxn = conn.prepareStatement(
                    "INSERT INTO transactions (from_account_id, to_account_id, type, amount, description, created_at) " +
                    "VALUES (?, NULL, 'withdraw', ?, ?, ?)");
                insertTxn.setInt(1, accountId);
                insertTxn.setDouble(2, amount);
                insertTxn.setString(3, "Withdraw money");
                insertTxn.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                insertTxn.executeUpdate();

                conn.commit(); // xác nhận transaction

                messageLabel.setStyle("-fx-text-fill: green;");
                messageLabel.setText("Rút tiền thành công!");
            } catch (Exception e) {
                conn.rollback();
                messageLabel.setText("Lỗi khi xử lý giao dịch!");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Không thể kết nối cơ sở dữ liệu!");
        }
    }
}

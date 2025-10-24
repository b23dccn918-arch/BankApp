package com.mycompany.bankonline.Database.Admin.PaymentBill;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.mycompany.bankonline.Database.Connect;
import com.mycompany.bankonline.Database.Admin.History.AdminHistoryFunction;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Model.Bill;
import com.mycompany.bankonline.Model.BillAdmin;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PaymentBillRepository {
	public static ObservableList<BillAdmin> getAllBills(){
		Connection con = Connect.getConnection();
		String sql = "select * from payment_bills";
		ObservableList<BillAdmin> bills = FXCollections.observableArrayList();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Long billId = rs.getLong("bill_id");
				int accountId = rs.getInt("account_id");
				Account acc = AdminPaymentBillFunction.getAccount(accountId);
				String accountNumber = acc.getAccountNumber();
				String billType = rs.getString("bill_type");
				BigDecimal amount = rs.getBigDecimal("amount");
				String status = rs.getString("status");
				Timestamp paidAt = rs.getTimestamp("paid_at");
				bills.add(new BillAdmin(billId, accountNumber, billType, amount, status, paidAt));
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bills;
	}
}

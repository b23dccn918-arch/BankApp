package com.mycompany.bankonline.Model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BillAdmin {
	private long billId;
	private String accountNumber;
	private String billType;
	private BigDecimal amount;
	private String status;
	private Timestamp paidAt;
	public long getBillId() {
		return billId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public String getBillType() {
		return billType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public String getStatus() {
		return status;
	}
	public Timestamp getPaidAt() {
		return paidAt;
	}
	
	
}

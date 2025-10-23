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
	
	public BillAdmin() {
		
	}
	
	public BillAdmin(long billId, String accountNumber, String billType, BigDecimal amount, String status,
			Timestamp paidAt) {
		super();
		this.billId = billId;
		this.accountNumber = accountNumber;
		this.billType = billType;
		this.amount = amount;
		this.status = status;
		this.paidAt = paidAt;
	}

	public long getBillId() {
		return billId;
	}

	public void setBillId(long billId) {
		this.billId = billId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(Timestamp paidAt) {
		this.paidAt = paidAt;
	}
		
	
}

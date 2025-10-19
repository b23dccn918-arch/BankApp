package com.mycompany.bankonline.Model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionAdmin {
	private Long transcationId;
	private String fromAccount, toAccount, type, description;
	private BigDecimal amount;
	private Timestamp createdAd;
	public TransactionAdmin(Long transcationId, String fromAccount, String toAccount, String type,BigDecimal amount, String description,
			 Timestamp createdAd) {
		super();
		this.transcationId = transcationId;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.type = type;
		this.amount = amount;
		this.description = description;
		this.createdAd = createdAd;
	}
	public Long getTranscationId() {
		return transcationId;
	}
	public String getFromAccount() {
		return fromAccount;
	}
	public String getToAccount() {
		return toAccount;
	}
	public String getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public Timestamp getCreatedAd() {
		return createdAd;
	}
	
	
}

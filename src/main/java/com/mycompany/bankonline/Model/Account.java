package com.mycompany.bankonline.Model;

import java.sql.Timestamp;

public class Account {
	private int accountId;
    private String username;
    private String password;
    private int userId;
	private String accountNumber;
    private long balance;
    private String pin;
    private int status;
    private Timestamp createdAt;
    
	public Account() {
		
	}

	public Account(int accountId, String username, String password, int userId, 
			String accountNumber, long balance, String pin, int status, Timestamp createdAt) {
		this.accountId = accountId;
		this.username = username;
		this.password = password;
		this.userId = userId;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.pin = pin;
		this.status = status;
		this.createdAt = createdAt;
	}

	private Account(Builder builder) {
        this.accountId = builder.accountId;
        this.username = builder.username;
        this.password = builder.password;
        this.userId = builder.userId;
        this.accountNumber = builder.accountNumber;
        this.balance = builder.balance;
        this.pin = builder.pin;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
    }

	public static class Builder {
        private int accountId;
        private String username;
        private String password;
        private int userId;
        private String accountNumber;
        private long balance;
        private String pin;
        private int status;
        private Timestamp createdAt;

        public Builder setAccountId(int accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder setBalance(long balance) {
            this.balance = balance;
            return this;
        }

        public Builder setPin(String pin) {
            this.pin = pin;
            return this;
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder setCreatedAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }



	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public String getPin() {
		return pin;
	}

	public void setPinID(String pinID) {
		this.pin = pinID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
    
	public Timestamp getCreatedAt() {
		return createdAt;
	}
    
}


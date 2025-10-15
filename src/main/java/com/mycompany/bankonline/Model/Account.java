package com.mycompany.bankonline.Model;


public class Account {
	private int accountId;
    private String username;
    private String password;
    private int userId;
	private String accountNumber;
    private long balance;
    private String pinID;
    private int status;
    
    public Account() {
    	
    }

	public Account(int accountId, String username, String password, int userId, String accountNumber, long balance,
			String pinID, int status) {
		super();
		this.accountId = accountId;
		this.username = username;
		this.password = password;
		this.userId = userId;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.pinID = pinID;
		this.status = status;
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

	public String getPinID() {
		return pinID;
	}

	public void setPinID(String pinID) {
		this.pinID = pinID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
    
  
    
}


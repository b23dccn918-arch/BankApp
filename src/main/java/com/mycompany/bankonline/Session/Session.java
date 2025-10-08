package com.mycompany.bankonline.Session;


//luu thong tin nguoi dung
public class Session {
    private static Session instance;
    private int userId;
    private int accountId;

    private Session() {}

    public void clear() {
        userId = 0;
        accountId = 0;
        System.out.println("Session cleared");
    }

    public static Session getInstance() {
        if (instance == null) instance = new Session();
        return instance;
    }

    public void setSession(int userId, int accountId) {
        this.userId = userId;
        this.accountId = accountId;
    }

    public int getUserId() { return userId; }
    public int getAccountId() {return accountId;}

    @Override
    public String toString(){
        return userId + " " + accountId;
    }
}


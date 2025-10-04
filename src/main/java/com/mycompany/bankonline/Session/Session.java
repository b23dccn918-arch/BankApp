package com.mycompany.bankonline.Session;


//luu thong tin nguoi dung
public class Session {
    private static Session instance;
    private int userId;
    private String fullName;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) instance = new Session();
        return instance;
    }

    public void setUser(int id, String name) {
        this.userId = id;
        this.fullName = name;
    }

    public int getUserId() { return userId; }
    public String getFullName() { return fullName; }
}


package com.mycompany.bankonline.Model;

import java.sql.Date;
import java.sql.Timestamp;

public class User {
    private int userId;
    private String fullName;
    private String citizenIdentifier;
    private String job;
    private String gender;
    private Date dateBirth;
    private String address;
    private String email;
    private Timestamp createdAt;
    public User(){}
    public User(int userId, String fullName, String citizenIdentifier, String job,
                String gender, Date dateBirth, String address, String email, Timestamp createdAt) {
        this.userId = userId;
        this.fullName = fullName;
        this.citizenIdentifier = citizenIdentifier;
        this.job = job;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.address = address;
        this.email = email;
        this.createdAt = createdAt;
    }

    public int getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getCitizenIdentifier() { return citizenIdentifier; }
    public String getJob() { return job; }
    public String getGender() { return gender; }
    public Date getDateBirth() { return dateBirth; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public Timestamp getCreatedAt() { return createdAt; }
}

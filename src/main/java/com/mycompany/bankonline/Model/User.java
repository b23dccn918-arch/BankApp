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

    private User(Builder builder) {
        this.userId = builder.userId;
        this.fullName = builder.fullName;
        this.citizenIdentifier = builder.citizenIdentifier;
        this.job = builder.job;
        this.gender = builder.gender;
        this.dateBirth = builder.dateBirth;
        this.address = builder.address;
        this.email = builder.email;
        this.createdAt = builder.createdAt;
    }

    public static class Builder {
        private int userId;
        private String fullName;
        private String citizenIdentifier;
        private String job;
        private String gender;
        private Date dateBirth;
        private String address;
        private String email;
        private Timestamp createdAt;

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder citizenIdentifier(String citizenIdentifier) {
            this.citizenIdentifier = citizenIdentifier;
            return this;
        }

        public Builder job(String job) {
            this.job = job;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder dateBirth(Date dateBirth) {
            this.dateBirth = dateBirth;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public User build() {
            return new User(this);
        }
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

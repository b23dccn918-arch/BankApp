package com.mycompany.bankonline.MainApp;


public class Customer {
    private String fullname;
    private String birth;
    private String citizenIdentification;
    private String gender;
    private String job;
    private String address;
    private String phoneNumber;
    
    public Customer() {
    	
    }

    public Customer(String fullname, String birth, String citizenIdentification,
                    String gender, String job, String address, String phoneNumber) {
        this.fullname = fullname;
        this.birth = birth;
        this.citizenIdentification = citizenIdentification;
        this.gender = gender;
        this.job = job;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCitizenIdentification() {
        return citizenIdentification;
    }

    public void setCitizenIdentification(String citizenIdentification) {
        this.citizenIdentification = citizenIdentification;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
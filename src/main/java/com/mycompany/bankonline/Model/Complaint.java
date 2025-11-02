package com.mycompany.bankonline.Model;


import java.sql.Time;
import java.sql.Timestamp;

public class Complaint {
    private int complaintId;
    private int accountId;
    private String subject;
    private String content;
    private String status;
    private String adminNote;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private Complaint(Builder builder) {
        this.complaintId = builder.complaintId;
        this.accountId = builder.accountId;
        this.subject = builder.subject;
        this.content = builder.content;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.adminNote = adminNote;
    }

    public static class Builder {
        private int complaintId;
        private int accountId;
        private String subject;
        private String content;
        private String status;
        private Timestamp createdAt;
        private Timestamp updatedAt;
        private String adminNote;

        public Builder() {}

        public Builder adminNote(String adminNote){
            this.adminNote = adminNote;
            return this;
        }

        public Builder complaintId(int complaintId) {
            this.complaintId = complaintId;
            return this;
        }

        public Builder accountId(int accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(Timestamp updatedAt){
            this.updatedAt = updatedAt;
            return this;
        }

        public Complaint build() {
            return new Complaint(this);
        }
    }

    public int getComplaintId() { return complaintId; }
    public int getAccountId() { return accountId; }
    public String getSubject() { return subject; }
    public String getContent() { return content; }
    public String getStatus() { return status; }
    public Timestamp getUpdatedAt(){
        return updatedAt;
    }
    public String getAdminNote(){
        return adminNote;
    }
    public Timestamp getCreatedAt() { return createdAt; }
}

package com.mycompany.bankonline.Controller.ComplaintDetail;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.mycompany.bankonline.Model.Complaint;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ComplaintDetailController {
    @FXML 
    private Label lblStatus;
    @FXML 
    private Label lblSubject;
    @FXML 
    private Label lblCreatedAt;
    @FXML 
    private TextArea txtContent;
    @FXML 
    private TextArea txtAdminNote;

    private Complaint complaint;



    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
        lblSubject.setText(complaint.getSubject());
        lblStatus.setText(complaint.getStatus());
        Timestamp timestamp = complaint.getCreatedAt();
        String formattedDate = (timestamp != null)
            ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp)
            : "--------------";
        lblCreatedAt.setText(formattedDate);
        txtContent.setText(complaint.getContent());
        txtAdminNote.setText(complaint.getAdminNote() == null ? "No response yet" : complaint.getAdminNote());
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) lblStatus.getScene().getWindow();
        stage.close();
    }
}

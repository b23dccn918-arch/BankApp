package com.mycompany.bankonline.Controller.Admin;

import com.mycompany.bankonline.Database.Account.AccountHandler;
import com.mycompany.bankonline.Database.Complaint.ComplaintHandler;
import com.mycompany.bankonline.Model.Account;
import com.mycompany.bankonline.Model.Complaint;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ComplaintDetailController {

    @FXML private Label lblStatus;
    @FXML private Label lblSubject;
    @FXML private Label lblCreatedAt;
    @FXML private TextArea txtContent;
    @FXML private TextArea txtAdminNote;
    @FXML private Label lblFromAccount;

    private final AccountHandler accountHandler = new AccountHandler();

    private Complaint complaint;
    private final ComplaintHandler complaintHandler = new ComplaintHandler();
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
        txtAdminNote.setText(complaint.getAdminNote() == null ? "" : complaint.getAdminNote());

        int accountId = complaint.getAccountId();

        Account AccountSendComplaint = accountHandler.findAccountByAccountId(accountId);

        lblFromAccount.setText(AccountSendComplaint.getAccountNumber());
    }

    @FXML
    private void markRejected() {
        updateStatus("rejected");
    }

    @FXML
    private void markResolved() {
        updateStatus("resolved");
    }

    private void updateStatus(String newStatus) {
        if (complaint == null) return;

        Boolean result = complaintHandler.updateStatusByComplaintId(complaint.getComplaintId(),newStatus);
        if(result){
            showAlert(Alert.AlertType.INFORMATION, "Updated", "Complaint Status has updated success");
        }
        else{
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update complaint");
        }
    }

    @FXML
    private void saveAdminNote() {
        if (complaint == null) return;

        String note = txtAdminNote.getText().trim();
        
        Boolean result = complaintHandler.updateAdminNoteByComplaintId(complaint.getComplaintId(), note);
        if(result){
            showAlert(Alert.AlertType.INFORMATION, "Saved", "Admin note updated successfully!");
        }
        else{
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save admin note");
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) lblStatus.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

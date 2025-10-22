package com.mycompany.bankonline.Service;


import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {

    private final String fromEmail = "b23dccn918@gmail.com";
    private final String appPassword = "xauf qnqs scnv mrab"; // App Password

    public void sendResetToken(String toEmail, String token) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Mã đặt lại mật khẩu");
        message.setText("Mã xác thực của bạn là: " + token + "\nMã có hiệu lực trong 5 phút.");

        Transport.send(message);
    }

    public void sendPinResetToken(String toEmail, String token) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Mã đặt lại mã pin");
        message.setText("Mã xác thực của bạn là: " + token + "\nMã có hiệu lực trong 5 phút.");

        Transport.send(message);
    }
}

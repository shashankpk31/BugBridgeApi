package com.shashankpk.BugBridgeApi.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.shashankpk.BugBridgeApi.DataInitializer;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
    @Autowired
    private JavaMailSender mailSender;
    
    private static final Logger logger=LoggerFactory.getLogger(EmailService.class);
    
    public void sendPasswordResetEmail(String to, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Bug Bridge: Password Reset Request");
        String resetUrl = "http://localhost:3000/reset-password?token=" + token;
        String htmlContent = "<div style=\"font-family: Arial, sans-serif; color: #333; line-height: 1.6;\">\n" +
                "    <h2 style=\"color: #0078d7;\">Hello,</h2>\n" +
                "    <p>You requested a password reset for Bug Bridge. Click the button below to reset your password:</p>\n" +
                "    <p>\n" +
                "        <a href=\"" + resetUrl + "\" style=\"background-color: #0078d7; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;\">\n" +
                "            Reset Password\n" +
                "        </a>\n" +
                "    </p>\n" +
                "    <p>This link expires in 1 hour. If you didn’t request this, ignore this email.</p>\n" +
                "    <p>Thanks,<br>Bug Bridge Team</p>\n" +
                "</div>";
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    public void sendWelcomeEmail(String to, String tempPassword) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Welcome to Bug Bridge!");
        String htmlContent = "<div style=\"font-family: Arial, sans-serif; color: #333; line-height: 1.6;\">\n" +
                "    <h2 style=\"color: #0078d7;\">Hello,</h2>\n" +
                "    <p>Welcome to Bug Bridge! Your account has been created. Here’s your temporary password:</p>\n" +
                "    <p style=\"font-size: 18px; font-weight: bold;\">" + tempPassword + "</p>\n" +
                "    <p>Please log in and reset your password using the 'Forgot Password' option.</p>\n" +
                "    <p>\n" +
                "        <a href=\"http://localhost:3000\" style=\"background-color: #0078d7; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;\">\n" +
                "            Log In to Bug Bridge\n" +
                "        </a>\n" +
                "    </p>\n" +
                "    <p>Thanks,<br>Bug Bridge Team</p>\n" +
                "</div>";
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    public void sendPasswordChangedEmail(String to) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Bug Bridge: Password Changed Successfully");
        String htmlContent = "<div style=\"font-family: Arial, sans-serif; color: #333; line-height: 1.6;\">\n" +
                "    <h2 style=\"color: #0078d7;\">Hello,</h2>\n" +
                "    <p>Your password for Bug Bridge has been successfully changed.</p>\n" +
                "    <p>If you didn’t make this change, please contact support immediately.</p>\n" +
                "    <p>Thanks,<br>Bug Bridge Team</p>\n" +
                "</div>";
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
}


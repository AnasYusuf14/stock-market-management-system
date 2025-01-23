package com.dev.stockmarketsystem.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    private final String from = "20360859117@ogr.btu.edu.tr"; 

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from); 
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendTestEmail() {
        sendEmail("anas.yusuf200112@gmail.com", "Test Email", "This is a test email from your application.");
        System.out.println("Email sent successfully!");
    }
}


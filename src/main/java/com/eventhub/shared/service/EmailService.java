package com.eventhub.shared.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to EventHub!");
        message.setText("Hello " + name + ",\n\nWelcome to EventHub! We're glad to have you on board.");
        mailSender.send(message);
    }

    public void sendSignInNotification(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Sign-In Notification");
        message.setText("Hello " + name + ",\n\nYour account was just signed in. If this wasn't you, please contact support.");
        mailSender.send(message);
    }
}


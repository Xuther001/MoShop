package com.ecommerce.MoShop.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendHelloMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("xuther85@gmail.com");
        message.setSubject("Hello from Spring Boot!");
        message.setText("Hello from Spring Boot!");
        message.setFrom("your-email@gmail.com");

        mailSender.send(message);
    }
}


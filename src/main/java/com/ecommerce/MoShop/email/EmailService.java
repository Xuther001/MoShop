package com.ecommerce.MoShop.email;

import com.ecommerce.MoShop.user.User;
import com.ecommerce.MoShop.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;


    //Right now we just return the username and password for simplicity
    public String sendUsernameAndPasswordResetLink(String email) {

        Optional<User> userOpt = userService.getUserByEmail(email);

        if (!userOpt.isPresent()) {
            return "User with this email not found.";
        }

        User user = userOpt.get();

        String subject = "Your Requested Username and Password Reset Link";
        String message = "Hello " + user.getUsername() + ",\n\n"
                + "Here is your username: " + user.getUsername();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        try {
            mailSender.send(mailMessage);
            return "Email sent successfully.";
        } catch (Exception e) {
            return "Failed to send email.";
        }
    }
}


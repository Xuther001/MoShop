package com.ecommerce.MoShop.email;

import com.ecommerce.MoShop.tokenresetpassword.ResetToken;
import com.ecommerce.MoShop.tokenresetpassword.ResetTokenService;
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

    private ResetTokenService resetTokenService;

    public EmailService(ResetTokenService resetTokenService) {
        this.resetTokenService = resetTokenService;
    }

    public String sendPasswordResetLink(String email) {
        Optional<User> userOpt = userService.getUserByEmail(email);

        if (!userOpt.isPresent()) {
            return "User with this email not found.";
        }

        User user = userOpt.get();

        resetTokenService.deleteExistingTokensForUser(user);

        ResetToken resetToken = resetTokenService.createResetTokenForUser(user);

        String resetLink = "http://localhost:8080/api/reset-password?token=" + resetToken.getToken();

        String message = "Hello " + user.getUsername() + ",\n\n"
                + "To reset your password, click the following link: " + resetLink;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Password Reset");
        mailMessage.setText(message);

        mailSender.send(mailMessage);

        return "Password reset link sent.";
    }
}


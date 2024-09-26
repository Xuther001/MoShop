package com.ecommerce.MoShop.requestpassword;

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
public class RequestPasswordService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    private ResetTokenService resetTokenService;

    public RequestPasswordService(ResetTokenService resetTokenService) {
        this.resetTokenService = resetTokenService;
    }

    public String sendPasswordResetLink(String email) {
        Optional<User> userOpt = userService.getUserByEmail(email);

        if (!userOpt.isPresent()) {
            return "User with this email not found.";
        }

        User user = userOpt.get();

        // Delete any existing reset tokens for the user
        resetTokenService.deleteExistingTokensForUser(user);

        // Create a new reset token for the user
        ResetToken resetToken = resetTokenService.createResetTokenForUser(user);

        // Change the link to point to the frontend (React app)
//        String resetLink = "http://localhost:3000/reset-password?token=" + resetToken.getToken();
        // AWS S3
        String resetLink = "http://moshop1.0.s3-website-us-west-2.amazonaws.com/reset-password?token=" + resetToken.getToken();

        // Construct the email message
        String message = "Hello " + user.getUsername() + ",\n\n"
                + "To reset your password, click the following link: " + resetLink;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Password Reset");
        mailMessage.setText(message);

        // Send the email
        mailSender.send(mailMessage);

        return "Password reset link sent.";
    }
}


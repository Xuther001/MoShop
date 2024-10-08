package com.ecommerce.MoShop.passwordreset;

import com.ecommerce.MoShop.tokenresetpassword.ResetTokenService;
import com.ecommerce.MoShop.user.User;
import com.ecommerce.MoShop.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PasswordResetController {

    @Autowired
    private ResetTokenService resetTokenService;

    @Autowired
    private UserService userService;

    @GetMapping("/reset-password")
    public ResponseEntity<?> showResetPasswordPage(@RequestParam String token) {
        boolean isValidToken = resetTokenService.verifyResetToken(token);
        if (!isValidToken) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"Invalid or expired token.\"}");
        }

        return ResponseEntity.ok("{\"message\": \"Token is valid. You can reset your password.\"}");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        boolean isValidToken = resetTokenService.verifyResetToken(token);

        if (!isValidToken) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"Invalid or expired token.\"}");
        }

        User user = resetTokenService.getUserByToken(token).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"User not found.\"}");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        userService.save(user);

        resetTokenService.deleteExistingTokensForUser(user);

        return ResponseEntity.ok("{\"message\": \"Password successfully reset.\"}");
    }
}
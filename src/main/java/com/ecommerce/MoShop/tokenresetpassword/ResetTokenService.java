package com.ecommerce.MoShop.tokenresetpassword;

import com.ecommerce.MoShop.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResetTokenService {

    @Autowired
    private ResetTokenRepository resetTokenRepository;

    public String generateResetToken() {
        return UUID.randomUUID().toString();
    }

    public ResetToken createResetTokenForUser(User user) {
        String token = generateResetToken();
        ResetToken resetToken = new ResetToken(token, user, LocalDateTime.now().plusHours(1));
        return resetTokenRepository.save(resetToken);
    }

    public boolean verifyResetToken(String token) {
        Optional<ResetToken> resetTokenOpt = resetTokenRepository.findByToken(token);

        if (resetTokenOpt.isPresent()) {
            ResetToken resetToken = resetTokenOpt.get();
            if (resetToken.getExpirationTime().isAfter(LocalDateTime.now())) {
                return true;
            } else {
                resetTokenRepository.delete(resetToken);
                return false;
            }
        }

        return false;
    }

    @Transactional
    public void deleteExistingTokensForUser(User user) {
        resetTokenRepository.deleteByUser(user);
    }

    public Optional<User> getUserByToken(String token) {
        Optional<ResetToken> resetTokenOpt = resetTokenRepository.findByToken(token);
        return resetTokenOpt.map(ResetToken::getUser);
    }
}
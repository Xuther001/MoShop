package com.ecommerce.MoShop.tokenresetpassword;

import com.ecommerce.MoShop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {

    Optional<ResetToken> findByToken(String token);

    void deleteByUser(User user);
}
package com.ecommerce.MoShop.tokenresetpassword;

import com.ecommerce.MoShop.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class ResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime expirationTime;

    public ResetToken() {
    }

    public ResetToken(String token, User user, LocalDateTime expirationTime) {
        this.token = token;
        this.user = user;
        this.expirationTime = expirationTime;
    }

}
package com.ecommerce.MoShop.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String username;
    private LocalDateTime timestamp;
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String country;

}

package com.ecommerce.MoShop.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must only contain letters")
    private String firstname;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must only contain letters")
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Username is required")
    private String username;

    private String streetAddress;

    @NotBlank(message = "City is required")
    @Pattern(regexp = "^[A-Za-z]+$", message = "City must only contain letters")
    private String city;

    @NotBlank(message = "State is required")
    @Pattern(regexp = "^[A-Za-z]+$", message = "State must only contain letters")
    private String state;

    @NotBlank(message = "Postal code is required")
    @Pattern(regexp = "^[0-9]+$", message = "Postal code must only contain numbers")
    private String postalCode;

    @NotBlank(message = "Country is required")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Country must only contain letters")
    private String country;
}
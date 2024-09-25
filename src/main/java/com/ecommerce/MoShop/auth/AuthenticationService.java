package com.ecommerce.MoShop.auth;

import com.ecommerce.MoShop.user.UserAddressRepository;
import com.ecommerce.MoShop.user.User;
import com.ecommerce.MoShop.common.security.Jwt.JwtService;
import com.ecommerce.MoShop.common.security.model.ERole;
import com.ecommerce.MoShop.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ecommerce.MoShop.user.UserAddress;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        String normalizedUsername = request.getUsername().toLowerCase();

        String email = request.getEmail();

        if (userRepository.existsByUsername(normalizedUsername)) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .username(normalizedUsername)
                .password(passwordEncoder.encode(request.getPassword()))
                .signupDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())) // Convert LocalDateTime to Date
                .role(ERole.ROLE_USER)
                .build();

        User savedUser = userRepository.save(user);

        if (request.getStreetAddress() != null) {
            UserAddress address = new UserAddress();
            address.setStreetAddress(request.getStreetAddress());
            address.setCity(request.getCity());
            address.setState(request.getState());
            address.setPostalCode(request.getPostalCode());
            address.setCountry(request.getCountry());
            address.setUser(savedUser);

            userAddressRepository.save(address);
        }

        String jwtToken = jwtService.generateToken(savedUser);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(savedUser.getUsername())
                .userId(savedUser.getId())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        String normalizedUsername = request.getUsername().toLowerCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        normalizedUsername,
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(normalizedUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .userId(user.getId())
                .build();
    }
}
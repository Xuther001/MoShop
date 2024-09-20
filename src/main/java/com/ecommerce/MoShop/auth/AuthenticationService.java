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

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserAddressRepository userAddressRepository;

    public AuthenticationResponse register(RegisterRequest request) {

        String username = request.getUsername();
        String email = request.getEmail();

        if (repository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (repository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .streetAddress(request.getStreetAddress())
                .city(request.getCity())
                .state(request.getState())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .signupDate(new Date())
                .role(ERole.ROLE_USER)
                .build();
        repository.save(user);

        var address = new UserAddress();
        address.setStreetAddress(request.getStreetAddress());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setUser(user);

        userAddressRepository.save(address);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .build();
    }
}

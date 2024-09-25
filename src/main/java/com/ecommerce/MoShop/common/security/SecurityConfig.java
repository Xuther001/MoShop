package com.ecommerce.MoShop.common.security;

import com.ecommerce.MoShop.common.security.Jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private final String[] whitelistedUrls = {
            "/api/v1/auth/**",
            "/api/v1/auth/authenticate",
            "/api/v1/notes/**",
            "/api/v1/notes",
            "/swagger-ui/**",
            "/v3/api-docs/swagger-config",
            "/v3/api-docs",
            "/api/products/**",
            "/api/users/**",
            "/email/sendHello",
            "/reset-password",
            "/request-password",
            "/api/reset-password",
            "/api/products/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and() // Enable CORS
                .csrf().disable() // Disable CSRF
                .authorizeHttpRequests()
                .requestMatchers(whitelistedUrls).permitAll() // Permit listed URLs
                .anyRequest().authenticated() // Other requests require authentication
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }

    // CORS configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Frontend origin
//        // For AWS
//        configuration.setAllowedOrigins(Arrays.asList("http://moshop1.0.s3-website-us-west-2.amazonaws.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Allowed methods
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Allowed headers
        configuration.setAllowCredentials(true); // Allow credentials (cookies, tokens)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS settings globally
        return source;
    }
}
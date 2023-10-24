package com.ecommerce.MoShop.common.security.service;

import com.ecommerce.MoShop.common.entity.User;
import com.ecommerce.MoShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository; // Inject your UserRepository to perform user-related database operations

    public Optional<User> findByUsername(String username) {
        // Implement the logic to find a user by username from your repository
        // For example, you can use a UserRepository to find the user by username.
        return userRepository.findByUsername(username);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // Handle the case where the user is not authenticated
            // You can return null or throw an exception, or handle it as needed.
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            return (User) principal;
        } else {
            // Handle the case where the principal is not an instance of your User class
            // You can return null or throw an exception, or handle it as needed.
            return null;
        }
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

}

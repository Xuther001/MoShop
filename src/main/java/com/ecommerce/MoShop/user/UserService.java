package com.ecommerce.MoShop.user;

import com.ecommerce.MoShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void addAddressToUser(Optional<User> optionalUser, Address newAddress) {
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getAddresses().size() < 5) {
                newAddress.setUser(user);
                user.getAddresses().add(newAddress);
                userRepository.save(user);
            } else {
                throw new AddressLimitExceededException("Maximum allowed addresses reached.");
            }
        } else {
            throw new IllegalArgumentException("User cannot be null.");
        }
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // Handle the case where the user is not authenticated
            // Can return null or throw an exception
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            return (User) principal;
        } else {
            // Handle the case where the principal is not an instance of User class
            // Can return null or throw an exception, or handle it as needed.
            return null;
        }
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}

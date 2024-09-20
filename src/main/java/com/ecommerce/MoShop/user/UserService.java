package com.ecommerce.MoShop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void addAddressToUser(Optional<User> user, UserAddress newUserAddress) {
        if (user.isPresent()) {
            newUserAddress.setUser(user.get());
            userAddressRepository.save(newUserAddress);  // Save address to the DB
        } else {
            throw new RuntimeException("User not found");
        }
    }

//    public List<UserAddress> getAddressesForCurrentUser() {
//        User currentUser = getCurrentUser();
//        if (currentUser != null) {
//            return userAddressRepository.findByUser(currentUser);
//        } else {
//            throw new RuntimeException("Current user not found or not authenticated");
//        }
//    }

    public List<UserAddress> getAddressesForUser(User user) {
        return userAddressRepository.findByUser(user);
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

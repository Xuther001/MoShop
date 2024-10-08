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

    public Optional<User> getUserByUserId(Long userId) {
        return userRepository.findById(String.valueOf(userId));
    }

    public void addAddressToUser(Optional<User> user, UserAddress newUserAddress) {
        if (user.isPresent()) {
            newUserAddress.setUser(user.get());
            userAddressRepository.save(newUserAddress);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public List<UserAddress> getAddressesForUser(User user) {
        return userAddressRepository.findByUser(user);
    }

    public Optional<User> getUserByEmail(String email) {return userRepository.findByEmail(email);}

    public Optional<User> getUserByUserId(String userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}

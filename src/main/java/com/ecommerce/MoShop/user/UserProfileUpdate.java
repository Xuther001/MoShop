package com.ecommerce.MoShop.user;

import com.ecommerce.MoShop.common.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserProfileUpdate {

    private final UserService userService;

    @Autowired
    public UserProfileUpdate(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User updatedUser) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameFromToken = authentication.getName();

        if (!username.equals(usernameFromToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Update Error: Forbidden. Provided username does not match authenticated user's username");
        }

        Optional<User> optionalUser = userService.getUserByUsername(username);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();

        // Update user information
        if (updatedUser.getFirstname() != null) {user.setFirstname(updatedUser.getFirstname());}
        if (updatedUser.getLastname() != null) {user.setLastname(updatedUser.getLastname());}
        if (updatedUser.getEmail() != null) {user.setEmail(updatedUser.getEmail());}
        if (updatedUser.getPassword() != null) {user.setPassword(updatedUser.getPassword());}
        if (updatedUser.getUsername() != null) {user.setUsername(updatedUser.getUsername());}
        if (updatedUser.getStreetAddress() != null) {user.setStreetAddress(updatedUser.getStreetAddress());}
        if (updatedUser.getCity() != null) {user.setCity(updatedUser.getCity());}
        if (updatedUser.getState() != null) {user.setState(updatedUser.getState());}
        if (updatedUser.getPostalCode() != null) {user.setPostalCode(updatedUser.getPostalCode());}
        if (updatedUser.getCountry() != null) {user.setCountry(updatedUser.getCountry());}

        User updatedUserInfo = userService.save(user);
        return ResponseEntity.ok(updatedUserInfo);
    }
}

package com.ecommerce.MoShop.user;

import com.ecommerce.MoShop.common.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> optionalUser = userService.getUserById(id.toString());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();

        // Update user information
        user.setFirstname(updatedUser.getFirstname());
        user.setLastname(updatedUser.getLastname());
        user.setEmail(updatedUser.getEmail());

        // Save the updated user
        User updatedUserInfo = userService.save(user);
        return ResponseEntity.ok(updatedUserInfo);
    }
}

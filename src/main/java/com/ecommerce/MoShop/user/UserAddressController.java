package com.ecommerce.MoShop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserAddressController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/users/{userId}/addresses")
    public ResponseEntity<String> addAddressToUser(@PathVariable String userId, @RequestBody Address newAddress) {
        Optional<User> user = userService.getUserById(userId);
        userService.addAddressToUser(user, newAddress);
        return ResponseEntity.ok("Address added successfully.");
    }

}

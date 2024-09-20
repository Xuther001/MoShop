package com.ecommerce.MoShop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/api/users/{userId}/addresses")
    public ResponseEntity<List<Address>> getAllAddresses(@PathVariable String userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getAddresses());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

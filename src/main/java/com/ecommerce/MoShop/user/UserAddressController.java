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

    @PostMapping("/api/users/{userId}/address")
    public ResponseEntity<String> addAddressToUser(@PathVariable String userId, @RequestBody UserAddress newUserAddress) {
        Optional<User> user = userService.getUserByUserId(userId);
        userService.addAddressToUser(user, newUserAddress);
        return ResponseEntity.ok("Address added successfully.");
    }

    @GetMapping("/api/users/{userId}/address")
    public ResponseEntity<List<UserAddressDTO>> getUserAddresses(@PathVariable String userId) {
        Optional<User> user = userService.getUserByUserId(userId);

        if (user.isPresent()) {
            List<UserAddress> addresses = userService.getAddressesForUser(user.get());

            if (!addresses.isEmpty()) {
                List<UserAddressDTO> addressDTOs = addresses.stream()
                        .map(address -> new UserAddressDTO(
                                address.getId(),
                                address.getStreetAddress(),
                                address.getCity(),
                                address.getState(),
                                address.getPostalCode(),
                                address.getCountry()))
                        .toList();

                return ResponseEntity.ok(addressDTOs);
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

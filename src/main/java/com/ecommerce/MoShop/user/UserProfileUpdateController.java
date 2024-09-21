package com.ecommerce.MoShop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserProfileUpdateController {

    private final UserService userService;
    private final UserAddressRepository userAddressRepository;

    @Autowired
    public UserProfileUpdateController(UserService userService, UserAddressRepository userAddressRepository) {
        this.userService = userService;
        this.userAddressRepository = userAddressRepository;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameFromToken = authentication.getName();

        Optional<User> optionalUser = userService.getUserByUsername(usernameFromToken);

        if (optionalUser.isEmpty() || !optionalUser.get().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Update Error: Forbidden. You can only update your own profile.");
        }

        Optional<User> existingUserOptional = userService.getUserByUserId(userId);

        if (existingUserOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = existingUserOptional.get();

        if (updatedUser.getFirstname() != null) { user.setFirstname(updatedUser.getFirstname()); }
        if (updatedUser.getLastname() != null) { user.setLastname(updatedUser.getLastname()); }
        if (updatedUser.getEmail() != null) { user.setEmail(updatedUser.getEmail()); }
        if (updatedUser.getUsername() != null) { user.setUsername(updatedUser.getUsername()); }
        if (updatedUser.getPassword() != null) { user.setPassword(updatedUser.getPassword()); }

        if (updatedUser.getUserAddresses() != null && !updatedUser.getUserAddresses().isEmpty()) {
            UserAddress userAddress = updatedUser.getUserAddresses().get(0);
            Optional<UserAddress> existingAddress = userAddressRepository.findById(String.valueOf(userAddress.getId()));
            if (existingAddress.isPresent()) {
                UserAddress addressToUpdate = existingAddress.get();
                addressToUpdate.setStreetAddress(userAddress.getStreetAddress());
                addressToUpdate.setCity(userAddress.getCity());
                addressToUpdate.setState(userAddress.getState());
                addressToUpdate.setPostalCode(userAddress.getPostalCode());
                addressToUpdate.setCountry(userAddress.getCountry());
                userAddressRepository.save(addressToUpdate);
            }
        }

        User updatedUserInfo = userService.save(user);

        UserResponseDTO responseDto = new UserResponseDTO(
                updatedUserInfo.getId(),
                updatedUserInfo.getFirstname(),
                updatedUserInfo.getLastname(),
                updatedUserInfo.getEmail(),
                updatedUserInfo.getUsername(),
                new UserResponseDTO.UserDto(updatedUserInfo.getId(), updatedUserInfo.getUsername())
        );

        return ResponseEntity.ok(responseDto);
    }
}
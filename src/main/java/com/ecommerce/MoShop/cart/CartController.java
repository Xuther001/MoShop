package com.ecommerce.MoShop.cart;

import com.ecommerce.MoShop.product.Product;
import com.ecommerce.MoShop.user.User;
import com.ecommerce.MoShop.product.ProductService;
import com.ecommerce.MoShop.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public CartController(CartService cartService, ProductService productService, UserService userService) {
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public Optional<Cart> getCart(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId.toString());
        return cartService.getCartForUser(user);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> addToCart(@PathVariable String userId, @RequestBody Long productId) {
        try {
            Long parsedUserId = Long.parseLong(userId);
            Optional<User> user = userService.getUserById(String.valueOf(parsedUserId));
            Optional<Product> product = productService.getProductById(productId);

            if (user.isPresent() && product.isPresent()) {
                cartService.addToCart(user, product);
                return ResponseEntity.ok("Product added to cart successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found.");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid userId format.");
        }
    }

    @DeleteMapping("/{userId}/{productId}")
    public void removeFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        Optional<User> user = userService.getUserById(userId.toString());
        Optional<Product> product = productService.getProductById(productId);
        cartService.removeFromCart(user, product);
    }

    //clear cart
}

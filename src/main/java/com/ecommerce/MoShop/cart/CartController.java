package com.ecommerce.MoShop.cart;

import com.ecommerce.MoShop.product.Product;
import com.ecommerce.MoShop.common.entity.User;
import com.ecommerce.MoShop.product.ProductService;
import com.ecommerce.MoShop.common.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void addToCart(@PathVariable String userId, @RequestBody Long productId) {
        Optional<User> user = userService.getUserById(userId);
        Optional<Product> product = productService.getProductById(productId);
        cartService.addToCart(user, product);
    }

    @DeleteMapping("/{userId}/{productId}")
    public void removeFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        Optional<User> user = userService.getUserById(userId.toString());
        Optional<Product> product = productService.getProductById(productId);
        cartService.removeFromCart(user, product);
    }
}

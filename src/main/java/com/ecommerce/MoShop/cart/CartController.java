package com.ecommerce.MoShop.cart;

import com.ecommerce.MoShop.product.Product;
import com.ecommerce.MoShop.product.ProductService;
import com.ecommerce.MoShop.user.User;
import com.ecommerce.MoShop.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, UserService userService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping("/add")
    public void addToCart(@RequestParam String username, @RequestParam Long productId, @RequestParam Integer quantity) {
        Optional<User> user = userService.getUserByUsername(username);
        Optional<Product> product = productService.getProductById(productId);

        if (user.isPresent() && product.isPresent()) {
            cartService.addToCart(user, product, quantity);
        } else {
            throw new IllegalArgumentException("User or Product not found");
        }
    }

    @PostMapping("/remove")
    public void removeFromCart(@RequestParam String username, @RequestParam Long productId) {
        Optional<User> user = userService.getUserByUsername(username);
        Optional<Product> product = productService.getProductById(productId);

        if (user.isPresent() && product.isPresent()) {
            cartService.removeFromCart(user, product);
        } else {
            throw new IllegalArgumentException("User or Product not found");
        }
    }
}

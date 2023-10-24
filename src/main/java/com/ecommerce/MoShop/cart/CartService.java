package com.ecommerce.MoShop.cart;

import com.ecommerce.MoShop.product.Product;
import com.ecommerce.MoShop.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Optional<Cart> getCartForUser(Optional<User> userOptional) {
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found"));
        return Optional.ofNullable(cartRepository.findByUser(Optional.ofNullable(user)));
    }

    public void addToCart(Optional<User> userOptional, Optional<Product> productOptional) {
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productOptional.orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Cart cart = cartRepository.findByUser(Optional.ofNullable(user));
        if (cart == null) {
            cart = new Cart(user);
        }
        cartRepository.save(cart);
    }

    public void removeFromCart(Optional<User> userOptional, Optional<Product> productOptional) {
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productOptional.orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Cart cart = cartRepository.findByUser(Optional.ofNullable(user));
        if (cart != null) {
            cart.removeProduct(Optional.ofNullable(product));
            cartRepository.save(cart);
        }
    }
}


package com.ecommerce.MoShop.cart;

import com.ecommerce.MoShop.product.Product;
import com.ecommerce.MoShop.product.ProductRepository;
import com.ecommerce.MoShop.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Optional<Cart> getCartForUser(Optional<User> userOptional) {
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found"));
        return cartRepository.findByUser(user);
    }

    public void addToCart(Optional<User> userOptional, Optional<Product> productOptional, Integer quantity) {
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productOptional.orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Cart cart = cartRepository.findByUser(user).orElse(new Cart(user));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem(cart, product, product.getName(), product.getImageUrl(), product.getDescription(), quantity);
            cart.addCartItem(cartItem);
        }

        cartRepository.save(cart);
    }

    public void removeFromCart(Optional<User> userOptional, Optional<Product> productOptional) {
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productOptional.orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart != null) {
            CartItem cartItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().equals(product))
                    .findFirst()
                    .orElse(null); 

            if (cartItem != null) {
                cart.removeCartItem(cartItem);
                cartRepository.save(cart);
            }
        }
    }
}

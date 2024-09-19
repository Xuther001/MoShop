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

    public void removeCart(Cart cart) {
        if (cart != null) {
            cartRepository.delete(cart);
        } else {
            throw new IllegalArgumentException("Cart not found");
        }
    }

    public void addToCart(Optional<User> userOptional, Optional<Product> productOptional, Integer quantity) {
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productOptional.orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }

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

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

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

    public void updateQuantity(Optional<User> userOptional, Optional<Product> productOptional, int newQuantity) {
        if (userOptional.isPresent() && productOptional.isPresent()) {
            User user = userOptional.get();
            Product product = productOptional.get();
            Optional<Cart> cartOptional = getCartForUser(Optional.of(user));

            if (cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
                Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                        .filter(item -> item.getProduct().equals(product))
                        .findFirst();

                if (cartItemOptional.isPresent()) {
                    CartItem cartItem = cartItemOptional.get();
                    int currentQuantity = cartItem.getQuantity();

                    // Refund the current quantity to the product stock
                    product.setStock(product.getStock() + currentQuantity);

                    if (newQuantity > 0) {
                        // Check if the stock can accommodate the new quantity
                        if (product.getStock() < newQuantity) {
                            throw new IllegalArgumentException("Insufficient stock available");
                        }

                        // Deduct the new quantity from stock
                        product.setStock(product.getStock() - newQuantity);

                        // Update the cart item's quantity
                        cartItem.setQuantity(newQuantity);
                    } else {
                        // Remove item from the cart if the new quantity is zero or less
                        cart.getCartItems().remove(cartItem);
                    }

                    // Recalculate the total price of the cart
                    cart.calculateTotalPrice();

                    // Save the updated product and cart
                    productRepository.save(product);
                    cartRepository.save(cart);
                } else {
                    throw new IllegalArgumentException("Item not found in cart");
                }
            } else {
                throw new IllegalArgumentException("Cart not found");
            }
        } else {
            throw new IllegalArgumentException("User or Product not found");
        }
    }

}

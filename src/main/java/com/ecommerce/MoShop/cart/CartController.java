package com.ecommerce.MoShop.cart;

import com.ecommerce.MoShop.product.Product;
import com.ecommerce.MoShop.product.ProductService;
import com.ecommerce.MoShop.user.User;
import com.ecommerce.MoShop.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping("/{username}")
    public CartResponseDTO getCartForUser(@PathVariable String username) {
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Cart cart = cartService.getCartForUser(Optional.ofNullable(user))
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        return convertToCartResponseDTO(cart);
    }

    private CartResponseDTO convertToCartResponseDTO(Cart cart) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setId(cart.getId());
        dto.setTotalPrice(cart.calculateTotalPrice());

        Set<CartResponseDTO.CartItemDTO> cartItemDTOs = cart.getCartItems().stream()
                .map(item -> {
                    CartResponseDTO.CartItemDTO itemDTO = new CartResponseDTO.CartItemDTO();
                    itemDTO.setId(item.getId());
                    itemDTO.setProductId(item.getProduct().getId());
                    itemDTO.setQuantity(item.getQuantity());
                    itemDTO.setTotalPrice(item.getTotalPrice());
                    return itemDTO;
                })
                .collect(Collectors.toSet());

        dto.setCartItems(cartItemDTOs);
        return dto;
    }

    @PostMapping("/add")
    public void addToCart(@RequestBody CartRequestDTO request) {
        Optional<User> user = userService.getUserByUsername(request.getUsername());
        Optional<Product> product = productService.getProductById(request.getProductId());

        if (user.isPresent() && product.isPresent()) {
            cartService.addToCart(user, product, request.getQuantity());
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

package com.ecommerce.MoShop.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class CartResponseDTO {
    private Long id;
    private BigDecimal totalPrice;
    private Set<CartItemDTO> cartItems;

    @Getter
    @Setter
    public static class CartItemDTO {
        private Long id;
        private Long productId;
        private Integer quantity;
        private BigDecimal totalPrice;
    }
}

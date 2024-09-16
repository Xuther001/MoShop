package com.ecommerce.MoShop.cart;

import com.ecommerce.MoShop.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private String name;
    private String imageUrl;
    private String description;

    public CartItem() {
    }

    public CartItem(Cart cart, Product product, String name, String imageUrl, String description, int quantity) {
        this.cart = cart;
        this.product = product;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}

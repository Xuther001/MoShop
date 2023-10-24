package com.ecommerce.MoShop.product;

import com.ecommerce.MoShop.cart.Cart;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private int stock;
    private int category;
    private BigDecimal price;

    @Getter
    @ManyToMany(mappedBy = "products")
    private Set<Cart> carts;

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }
}

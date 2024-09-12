package com.ecommerce.MoShop.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int stock;
    private int category;
    private BigDecimal price;

    public void decrementStock(int quantityToDecrement) {
        if (quantityToDecrement > stock) {
            throw new IllegalArgumentException("Not enough stock available for this product.");
        }
        stock -= quantityToDecrement;
    }
}

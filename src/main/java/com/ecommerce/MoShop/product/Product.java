package com.ecommerce.MoShop.product;

import com.ecommerce.MoShop.category.Category;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String imageUrl;
    private String description;
    private int stock;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public void decrementStock(int quantityToDecrement) {
        if (quantityToDecrement > stock) {
            throw new IllegalArgumentException("Not enough stock available for this product.");
        }
        stock -= quantityToDecrement;
    }
}
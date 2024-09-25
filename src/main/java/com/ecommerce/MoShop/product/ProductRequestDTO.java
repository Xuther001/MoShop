package com.ecommerce.MoShop.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequestDTO {

    private String name;
    private Long category;
    private String imageUrl;
    private String description;
    private Integer stock;
    private BigDecimal price;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String name, Long category, String imageUrl, String description, Integer stock, BigDecimal price) {
        this.name = name;
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }
}
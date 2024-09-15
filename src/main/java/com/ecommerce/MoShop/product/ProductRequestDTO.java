package com.ecommerce.MoShop.product;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductRequestDTO {

    private String name;
    private String imageUrl;
    private String description;
    private Integer stock;
    private BigDecimal price;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String name, String imageUrl, String description, int stock, BigDecimal price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
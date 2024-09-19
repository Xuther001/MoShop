package com.ecommerce.MoShop.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequestDTO {

    private Long productId;
    private String name;
    private String imageUrl;
    private String description;
    private Integer stock;
    private BigDecimal price;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String name, Long productId, String imageUrl, String description, int stock, BigDecimal price) {
        this.name = name;
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }

//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setProductId(int productId) {
//        this.productId = productId;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setStock(int stock) {
//        this.stock = stock;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }
}
package com.ecommerce.MoShop.sale;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO {
    private Long productId;
    private String productName;
    private String description;
    private int quantity;
    private int category;
    private BigDecimal price;
}


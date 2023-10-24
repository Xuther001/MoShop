package com.ecommerce.MoShop.common.entity;

import com.ecommerce.MoShop.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Setter
@Getter
@Table(name = "sale")
@Data
public class Sale {

    @Id
    @GeneratedValue
    private Long id;

    private Date saleDate;

    private BigDecimal totalPrice;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "sale_product",
            joinColumns = @JoinColumn(name = "sale_id")
//            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    public Sale() {

    }

    public Sale(Date saleDate, BigDecimal totalPrice, Product product, User user) {
        this.saleDate = saleDate;
        this.totalPrice = totalPrice;
//        this.product = product;
        this.user = user;
    }
}

package com.ecommerce.MoShop.cart;

import com.ecommerce.MoShop.common.entity.User;
import com.ecommerce.MoShop.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Optional<Product> product) {
        products.remove(product);
    }

    public BigDecimal calculateTotalPrice() {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
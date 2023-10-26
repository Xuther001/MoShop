package com.ecommerce.MoShop.sale;

import com.ecommerce.MoShop.product.Product;
import com.ecommerce.MoShop.product.ProductRepository;
import com.ecommerce.MoShop.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "sale_invoice")
public class SaleInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date invoiceDate;

    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;

    @ElementCollection
    @CollectionTable(name = "invoice_product", joinColumns = @JoinColumn(name = "invoice_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Long, Integer> products = new HashMap<>();

    public SaleInvoice() {
        this.invoiceDate = new Date();
    }

    public SaleInvoice(User user, Map<Long, Integer> productQuantities, ProductRepository productRepository) {
        this();
        this.user = user;
        this.totalPrice = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

            if (product.getStock() >= quantity) {
                product.decrementStock(quantity);
                products.put(productId, quantity);
                totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            } else {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }
        }
    }
}

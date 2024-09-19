package com.ecommerce.MoShop.invoice;

import com.ecommerce.MoShop.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Setter
@Getter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private Set<InvoiceItem> items;

    private BigDecimal totalPrice;

    private LocalDateTime purchaseDate;

    private String paymentStatus;

}

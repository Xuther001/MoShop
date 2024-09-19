package com.ecommerce.MoShop.invoice;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
public class InvoiceDTO {

    private Long id;
    private BigDecimal totalPrice;
    private LocalDateTime purchaseDate;
    private String paymentStatus;
    private Set<InvoiceItemDTO> items;

    @Setter
    @Getter
    public static class InvoiceItemDTO {
        private Long id;
        private Long productId;
        private Integer quantity;
        private BigDecimal totalPrice;
    }
}
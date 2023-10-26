package com.ecommerce.MoShop.sale;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class SaleInvoiceResponseDTO {
    private Long id;
    private Date invoiceDate;
    private BigDecimal totalPrice;
    private SimplifiedUserDTO user;
    private Map<Long, Integer> products;

    private List<ProductDTO> productDetails;

    public void setProductDetails(List<ProductDTO> productDetails) {
        this.productDetails = productDetails;
    }
}


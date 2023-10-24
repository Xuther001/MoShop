package com.ecommerce.MoShop.sale;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SaleRequestDTO {

    private Long userId;
    private List<Long> productIds;

}


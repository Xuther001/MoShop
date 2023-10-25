package com.ecommerce.MoShop.sale;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class SaleRequestDTO {

    private Long userId;
    private Map<Long, Integer> productAndQuantities;

}


package com.ecommerce.MoShop.sale;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SaleRequestDTO {

    private Long userId;
    private Map<Long, Integer> productAndQuantities;

}

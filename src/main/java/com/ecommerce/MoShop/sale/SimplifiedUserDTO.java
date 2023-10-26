package com.ecommerce.MoShop.sale;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SimplifiedUserDTO {
    private Long id;

    public SimplifiedUserDTO(Long id) {
        this.id = id;
    }

}


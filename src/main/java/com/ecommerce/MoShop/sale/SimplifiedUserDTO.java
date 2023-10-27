package com.ecommerce.MoShop.sale;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SimplifiedUserDTO {

    private Long id;
    private String firstname;
    private String lastname;

    public SimplifiedUserDTO(Long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

}


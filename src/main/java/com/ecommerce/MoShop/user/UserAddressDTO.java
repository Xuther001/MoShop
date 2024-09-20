package com.ecommerce.MoShop.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressDTO {
    private Long id;
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
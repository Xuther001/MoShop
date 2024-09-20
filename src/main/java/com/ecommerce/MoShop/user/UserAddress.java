package com.ecommerce.MoShop.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "address")
public class UserAddress {

    @Id
    @GeneratedValue
    private Long id;
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

package com.ecommerce.MoShop.cart;

import com.ecommerce.MoShop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems ci WHERE c.user = :user")
    Optional<Cart> findByUser(@Param("user") User user);
}

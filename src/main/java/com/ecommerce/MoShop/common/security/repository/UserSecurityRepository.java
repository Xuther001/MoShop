package com.ecommerce.MoShop.common.security.repository;

import com.ecommerce.MoShop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSecurityRepository extends JpaRepository <User, Integer> {

    User findByEmail(String email);
}

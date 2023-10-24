package com.ecommerce.MoShop.common.security.repository;

import com.ecommerce.MoShop.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSecurityRepository extends JpaRepository <User, Integer> {

    User findByEmail(String email);
}

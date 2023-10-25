package com.ecommerce.MoShop.repository;

import com.ecommerce.MoShop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}

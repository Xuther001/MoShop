package com.ecommerce.MoShop.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT i FROM Invoice i WHERE i.user.username = :username")
    List<Invoice> findByUsername(@Param("username") String username);

    @Query("SELECT i FROM Invoice i WHERE i.user.id = :userId")
    List<Invoice> findByUserId(@Param("userId") Long userId);
}
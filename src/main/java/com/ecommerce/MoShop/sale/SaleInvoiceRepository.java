package com.ecommerce.MoShop.sale;

import com.ecommerce.MoShop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleInvoiceRepository extends JpaRepository<SaleInvoice, Long> {
    List<SaleInvoice> findByUser(User user);
}

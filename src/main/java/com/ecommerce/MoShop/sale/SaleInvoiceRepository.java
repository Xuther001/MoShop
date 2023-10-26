package com.ecommerce.MoShop.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleInvoiceRepository extends JpaRepository<SaleInvoice, Long> {
}

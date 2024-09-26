package com.ecommerce.MoShop.purchaseCheckforReview;

import com.ecommerce.MoShop.invoice.Invoice;
import com.ecommerce.MoShop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findFirstByUserAndItems_ProductId(User user, Long productId);
}

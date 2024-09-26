package com.ecommerce.MoShop.purchaseCheckforReview;

import com.ecommerce.MoShop.invoice.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public PurchaseService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public boolean checkIfUserPurchasedProduct(String username, Long productId) {
        return invoiceRepository.findAll().stream()
                .filter(invoice -> invoice.getUser().getUsername().equals(username))
                .flatMap(invoice -> invoice.getItems().stream())
                .anyMatch(item -> item.getProduct().getId().equals(productId));
    }
}

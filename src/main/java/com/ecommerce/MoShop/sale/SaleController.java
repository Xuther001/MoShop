package com.ecommerce.MoShop.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping("/create")
    public ResponseEntity<String> createSale(@RequestBody SaleRequestDTO saleRequestDTO) {
        saleService.processSale(saleRequestDTO);
        return ResponseEntity.ok("Sale created successfully!");
    }

    @GetMapping("/invoice/{id}")
    public ResponseEntity<SaleInvoice> getInvoiceDetails(@PathVariable Long id) {
        SaleInvoice saleInvoice = saleService.getSaleInvoiceDetails(id);
        return ResponseEntity.ok(saleInvoice);
    }

    @GetMapping("/invoices/user/{userId}")
    public ResponseEntity<List<SaleInvoice>> getAllInvoicesForUser(@PathVariable Long userId) {
        List<SaleInvoice> invoices = saleService.getAllInvoicesForUser(userId);
        return ResponseEntity.ok(invoices);
    }
}

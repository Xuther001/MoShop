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
    public ResponseEntity<SaleInvoiceResponseDTO> getInvoiceDetails(@PathVariable Long id) {
        SaleInvoiceResponseDTO saleInvoice = saleService.getSaleInvoiceDetails(id);
        return ResponseEntity.ok(saleInvoice);
    }

    @GetMapping("/invoices/user/{userId}")
    public ResponseEntity<List<SaleInvoiceResponseDTO>> getAllInvoicesForUser(@PathVariable Long userId) {
        List<SaleInvoiceResponseDTO> responseDTOs = saleService.getAllInvoicesForUser(userId);
        return ResponseEntity.ok(responseDTOs);
    }
}

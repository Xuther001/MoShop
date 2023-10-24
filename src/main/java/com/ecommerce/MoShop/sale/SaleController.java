package com.ecommerce.MoShop.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}


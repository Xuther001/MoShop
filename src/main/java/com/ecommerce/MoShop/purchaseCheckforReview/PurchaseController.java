package com.ecommerce.MoShop.purchaseCheckforReview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/check")
    public ResponseEntity<PurchaseCheckResponse> checkUserPurchase(
            @RequestParam String username,
            @RequestParam Long productId) {
        boolean purchased = purchaseService.checkIfUserPurchasedProduct(username, productId);
        return ResponseEntity.ok(new PurchaseCheckResponse(purchased));
    }
}
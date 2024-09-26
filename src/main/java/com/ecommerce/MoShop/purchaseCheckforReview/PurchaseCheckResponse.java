package com.ecommerce.MoShop.purchaseCheckforReview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseCheckResponse {
    private boolean purchased;

    public PurchaseCheckResponse(boolean purchased) {
        this.purchased = purchased;
    }
}
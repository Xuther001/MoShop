package com.ecommerce.MoShop.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewProductDTO {

    private Long id;
    private Long productId;
    private String comment;
    private int rating;

    public ReviewProductDTO(Long id, Long productId, String comment, int rating) {
        this.id = id;
        this.productId = productId;
        this.comment = comment;
        this.rating = rating;
    }

    public static ReviewProductDTO fromReview(Review review) {
        return new ReviewProductDTO(
                review.getId(),
                review.getProduct().getId(),
                review.getComment(),
                review.getRating()
        );
    }
}

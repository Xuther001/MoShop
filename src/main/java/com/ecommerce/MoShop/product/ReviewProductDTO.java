package com.ecommerce.MoShop.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewProductDTO {

    private Long id;
    private Long productId;
    private String username;
    private String comment;
    private int rating;

    public ReviewProductDTO(Long id, Long productId, String username, String comment, int rating) {
        this.id = id;
        this.productId = productId;
        this.username = username;
        this.comment = comment;
        this.rating = rating;
    }

    public static ReviewProductDTO fromReview(Review review) {
        return new ReviewProductDTO(
                review.getId(),
                review.getProduct().getId(),
                review.getUsername(),
                review.getComment(),
                review.getRating()
        );
    }
}

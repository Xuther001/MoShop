package com.ecommerce.MoShop.productReview;

import com.ecommerce.MoShop.product.Product;
import com.ecommerce.MoShop.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;

    @Autowired
    public ReviewController(ReviewService reviewService, ProductService productService) {
        this.reviewService = reviewService;
        this.productService = productService;
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewProductDTO> addReview(@RequestBody ReviewProductDTO reviewRequestDTO) {
        Product product = productService.getProductById(reviewRequestDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (reviewRequestDTO.getRating() < 1 || reviewRequestDTO.getRating() > 5) {
            return ResponseEntity.badRequest().body(null); // Handle this properly in your frontend
        }

        Review review = new Review();
        review.setUsername(reviewRequestDTO.getUsername());
        review.setProduct(product);
        review.setComment(reviewRequestDTO.getComment());
        review.setRating(reviewRequestDTO.getRating());

        Review savedReview = reviewService.saveReview(review);
        ReviewProductDTO responseDTO = ReviewProductDTO.fromReview(savedReview);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{productId}/reviews")
    public List<ReviewProductDTO> getReviewsByProduct(@PathVariable Long productId) {
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return reviewService.getReviewsByProduct(product)
                .stream()
                .map(ReviewProductDTO::fromReview)
                .collect(Collectors.toList());
    }

    // New method to get user reviews for a specific product
    @GetMapping("/{productId}/reviews/user")
    public ResponseEntity<List<ReviewProductDTO>> getUserReviews(
            @PathVariable Long productId, @RequestParam String username) {
        List<Review> userReviews = reviewService.getUserReviews(productId, username);
        List<ReviewProductDTO> responseDTOs = userReviews.stream()
                .map(ReviewProductDTO::fromReview)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}

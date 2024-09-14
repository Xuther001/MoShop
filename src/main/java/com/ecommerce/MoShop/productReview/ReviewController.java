package com.ecommerce.MoShop.productReview;

import com.ecommerce.MoShop.product.Product;
import com.ecommerce.MoShop.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<Review> addReview(@RequestBody ReviewProductDTO reviewRequestDTO) {
        // Fetch product using productId from the DTO
        Product product = productService.getProductById(reviewRequestDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Create and populate Review entity
        Review review = new Review();
        review.setUsername(reviewRequestDTO.getUsername());
        review.setProduct(product);
        review.setComment(reviewRequestDTO.getComment());
        review.setRating(reviewRequestDTO.getRating());

        // Save the review
        Review savedReview = reviewService.saveReview(review);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping("/{productId}/reviews")
    public List<ReviewProductDTO> getReviewsByProduct(@PathVariable Long productId) {
        Optional<Product> product = productService.getProductById(productId);
        if (product.isPresent()) {
            return reviewService.getReviewsByProduct(Optional.of(product.get()))
                    .stream()
                    .map(ReviewProductDTO::fromReview)
                    .collect(Collectors.toList());
        } else {
            // You could return an empty list or handle this case differently
            return List.of();
        }
    }
}

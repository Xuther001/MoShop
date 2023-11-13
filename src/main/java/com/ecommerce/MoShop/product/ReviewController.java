package com.ecommerce.MoShop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping
    @RequestMapping("/reviews")
    public Review addReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }

    @GetMapping
    @RequestMapping("/{productId}/reviews")
    public List<Review> getReviewsByProduct(@PathVariable Long productId) {
        Optional<Product> product = productService.getProductById(productId);
        return reviewService.getReviewsByProduct(product);
    }
}

package com.ecommerce.MoShop.productReview;

import com.ecommerce.MoShop.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);

    List<Review> findByProductIdAndUsername(Long productId, String username);
}
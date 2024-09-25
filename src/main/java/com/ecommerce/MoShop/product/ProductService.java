package com.ecommerce.MoShop.product;

import com.ecommerce.MoShop.category.Category;
import com.ecommerce.MoShop.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Product addProduct(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setImageUrl(productRequestDTO.getImageUrl());
        product.setDescription(productRequestDTO.getDescription());
        product.setStock(productRequestDTO.getStock());
        product.setPrice(productRequestDTO.getPrice());

        Optional<Category> categoryOptional = categoryRepository.findById(productRequestDTO.getCategory());
        if (categoryOptional.isPresent()) {
            product.setCategory(categoryOptional.get()); // Set the category if found
        } else {
            throw new IllegalArgumentException("Invalid category ID: " + productRequestDTO.getCategory());
        }

        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public void removeProductById(Long productId) {
        productRepository.deleteById(productId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
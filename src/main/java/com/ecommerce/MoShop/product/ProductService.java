package com.ecommerce.MoShop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(ProductRequestDTO productRequestDTO) {
        // Create a new Product entity from the request data
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setStock(productRequestDTO.getStock());
        product.setPrice(productRequestDTO.getPrice());
        // Add more properties if needed

        // Save the product to the database
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        // Retrieve and return a list of all products from the database
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long productId) {
        // Retrieve a product by its unique identifier (ID) from the database
        return productRepository.findById(productId);
    }
}
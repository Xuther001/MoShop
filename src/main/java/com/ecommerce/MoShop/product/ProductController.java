package com.ecommerce.MoShop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return productService.addProduct(productRequestDTO);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        Optional<Product> optionalProduct = productService.getProductById(productId);

        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found with ID: " + productId);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> editProduct(@PathVariable Long productId, @RequestBody ProductRequestDTO productRequestDTO) {

        Optional<Product> optionalProduct = productService.getProductById(productId);
        if (optionalProduct.isPresent()) {

            Product existingProduct = optionalProduct.get();
            if(productRequestDTO.getName() != null) {
            existingProduct.setName(productRequestDTO.getName());}
            if(productRequestDTO.getImageUrl() != null) {
            existingProduct.setImageUrl(productRequestDTO.getImageUrl());}
            if(productRequestDTO.getDescription() != null) {
            existingProduct.setDescription(productRequestDTO.getDescription());}
            if(productRequestDTO.getStock() != null) {
            existingProduct.setStock(productRequestDTO.getStock());}
            if(productRequestDTO.getPrice() != null) {
            existingProduct.setPrice(productRequestDTO.getPrice());}

            productService.updateProduct(existingProduct);
            return ResponseEntity.ok("Product updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + productId);
        }
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable Long productId) {
        Optional<Product> optionalProduct = productService.getProductById(productId);
        if (optionalProduct.isPresent()) {
            productService.removeProductById(productId);
            return ResponseEntity.ok("Product removed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + productId);
        }
    }
}

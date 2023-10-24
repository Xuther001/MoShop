package com.ecommerce.MoShop.sale;

import com.ecommerce.MoShop.product.Product;
import com.ecommerce.MoShop.common.entity.Sale;
import com.ecommerce.MoShop.common.entity.User;
import com.ecommerce.MoShop.product.ProductRepository;
import com.ecommerce.MoShop.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public void processSale(SaleRequestDTO saleRequestDTO) {
        User user = userRepository.findById(saleRequestDTO.getUserId().toString()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Sale sale = new Sale();
        sale.setSaleDate(new Date());
        sale.setUser(user);

        BigDecimal totalPrice = BigDecimal.valueOf(0.0);
        Set<Product> products = new HashSet<>();
        for (Long productId : saleRequestDTO.getProductIds()) {
            Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found"));
            products.add(product);
            totalPrice = totalPrice.add(product.getPrice());
        }

        sale.setProducts(products);
        sale.setTotalPrice(totalPrice);

        saleRepository.save(sale);
    }
}


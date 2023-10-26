package com.ecommerce.MoShop.sale;

import com.ecommerce.MoShop.product.ProductRepository;
import com.ecommerce.MoShop.repository.UserRepository;
import com.ecommerce.MoShop.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

    @Autowired
    private SaleInvoiceRepository saleInvoiceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public void processSale(SaleRequestDTO saleRequestDTO) {
        User user = userRepository.findById(saleRequestDTO.getUserId().toString())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        SaleInvoice saleInvoice = new SaleInvoice(user, saleRequestDTO.getProductAndQuantities(), productRepository);

        saleInvoiceRepository.save(saleInvoice);
    }
}

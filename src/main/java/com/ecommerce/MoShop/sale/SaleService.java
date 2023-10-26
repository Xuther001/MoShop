package com.ecommerce.MoShop.sale;

import com.ecommerce.MoShop.product.Product;
import com.ecommerce.MoShop.product.ProductRepository;
import com.ecommerce.MoShop.repository.UserRepository;
import com.ecommerce.MoShop.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SaleService {

    @Autowired
    private SaleInvoiceRepository saleInvoiceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public SaleInvoice getSaleInvoiceDetails(Long invoiceId) {
        SaleInvoice saleInvoice = saleInvoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));

        List<ProductDTO> productDetails = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : saleInvoice.getProducts().entrySet()) {
            Long productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(product.getId());
            productDTO.setProductName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setQuantity(quantity);
            productDTO.setCategory(product.getCategory());
            productDTO.setPrice(product.getPrice());

            productDetails.add(productDTO);
        }

        saleInvoice.setProductDetails(productDetails);
        return saleInvoice;
    }

    public List<SaleInvoice> getAllInvoicesForUser(Long userId) {
        User user = userRepository.findById(userId.toString())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return saleInvoiceRepository.findByUser(user);
    }

    public void processSale(SaleRequestDTO saleRequestDTO) {
        User user = userRepository.findById(saleRequestDTO.getUserId().toString())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        SaleInvoice saleInvoice = new SaleInvoice(user, saleRequestDTO.getProductAndQuantities(), productRepository);

        saleInvoiceRepository.save(saleInvoice);
    }
}

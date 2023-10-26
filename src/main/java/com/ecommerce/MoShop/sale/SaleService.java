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

    public SaleInvoiceResponseDTO getSaleInvoiceDetails(Long invoiceId) {
        SaleInvoice saleInvoice = saleInvoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));

        SaleInvoiceResponseDTO responseDTO = new SaleInvoiceResponseDTO();
        responseDTO.setId(saleInvoice.getId());
        responseDTO.setInvoiceDate(saleInvoice.getInvoiceDate());
        responseDTO.setTotalPrice(saleInvoice.getTotalPrice());

        User user = saleInvoice.getUser();
        SimplifiedUserDTO simplifiedUserDTO = new SimplifiedUserDTO(user.getId());
        responseDTO.setUser(simplifiedUserDTO);

        responseDTO.setProducts(saleInvoice.getProducts());

        List<ProductDTO> productDetails = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : saleInvoice.getProducts().entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(productId);
            productDTO.setProductName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setQuantity(quantity);
            productDTO.setCategory(product.getCategory());
            productDTO.setPrice(product.getPrice());

            productDetails.add(productDTO);
        }

        responseDTO.setProductDetails(productDetails);
        return responseDTO;
    }

    public List<SaleInvoiceResponseDTO> getAllInvoicesForUser(Long userId) {
        User user = userRepository.findById(userId.toString())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<SaleInvoice> invoices = saleInvoiceRepository.findByUser(user);
        List<SaleInvoiceResponseDTO> responseDTOs = new ArrayList<>();

        for (SaleInvoice invoice : invoices) {
            SaleInvoiceResponseDTO responseDTO = new SaleInvoiceResponseDTO();
            responseDTO.setId(invoice.getId());
            responseDTO.setInvoiceDate(invoice.getInvoiceDate());
            responseDTO.setTotalPrice(invoice.getTotalPrice());
            responseDTO.setUser(new SimplifiedUserDTO(user.getId()));
            responseDTO.setProducts(invoice.getProducts());

            List<ProductDTO> productDetails = new ArrayList<>();
            for (Map.Entry<Long, Integer> entry : invoice.getProducts().entrySet()) {
                Long productId = entry.getKey();
                Integer quantity = entry.getValue();
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new EntityNotFoundException("Product not found"));

                ProductDTO productDTO = new ProductDTO();
                productDTO.setProductId(productId);
                productDTO.setProductName(product.getName());
                productDTO.setDescription(product.getDescription());
                productDTO.setQuantity(quantity);
                productDTO.setCategory(product.getCategory());
                productDTO.setPrice(product.getPrice());
                productDetails.add(productDTO);
            }

            responseDTO.setProductDetails(productDetails);
            responseDTOs.add(responseDTO);
        }

        return responseDTOs;
    }

    public void processSale(SaleRequestDTO saleRequestDTO) {
        User user = userRepository.findById(saleRequestDTO.getUserId().toString())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        SaleInvoice saleInvoice = new SaleInvoice(user, saleRequestDTO.getProductAndQuantities(), productRepository);

        saleInvoiceRepository.save(saleInvoice);
    }
}

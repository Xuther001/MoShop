package com.ecommerce.MoShop.invoice;

import com.ecommerce.MoShop.cart.Cart;
import com.ecommerce.MoShop.cart.CartItem;
import com.ecommerce.MoShop.cart.CartService;
import com.ecommerce.MoShop.product.Product;
import com.ecommerce.MoShop.product.ProductService;
import com.ecommerce.MoShop.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CartService cartService;
    private final ProductService productService;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, CartService cartService, ProductService productService) {
        this.invoiceRepository = invoiceRepository;
        this.cartService = cartService;
        this.productService = productService;
    }

    // Method to get invoices by username
    public List<Invoice> getInvoicesByUsername(String username) {
        return invoiceRepository.findByUsername(username);
    }

    @Transactional
    public Invoice createInvoice(User user) {
        Optional<Cart> cartOptional = cartService.getCartForUser(Optional.of(user));

        if (cartOptional.isEmpty()) {
            throw new IllegalArgumentException("Cart not found for user.");
        }

        Cart cart = cartOptional.get();
        Set<CartItem> cartItems = cart.getCartItems();

        Invoice invoice = new Invoice();
        invoice.setUser(user);
        invoice.setPurchaseDate(LocalDateTime.now());
        invoice.setPaymentStatus("PAID");

        Set<InvoiceItem> invoiceItems = new HashSet<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            if (cartItem.getQuantity() > product.getStock()) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }

            // Deduct stock
            product.setStock(product.getStock() - cartItem.getQuantity());
            productService.updateProduct(product);

            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setInvoice(invoice);
            invoiceItem.setProduct(product);
            invoiceItem.setQuantity(cartItem.getQuantity());
            invoiceItem.setTotalPrice(cartItem.getTotalPrice());
            invoiceItems.add(invoiceItem);

            totalPrice = totalPrice.add(cartItem.getTotalPrice());
        }

        invoice.setItems(invoiceItems);
        invoice.setTotalPrice(totalPrice);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        cartService.removeCart(cart);

        return savedInvoice;
    }
}
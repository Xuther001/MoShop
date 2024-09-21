package com.ecommerce.MoShop.invoice;

import com.ecommerce.MoShop.user.User;
import com.ecommerce.MoShop.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final UserService userService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, UserService userService) {
        this.invoiceService = invoiceService;
        this.userService = userService;
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam String username) {
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        invoiceService.createInvoice(user);
        return "Transaction Successful";
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<InvoiceDTO>> getInvoices(@PathVariable Long userId) {
        List<Invoice> invoices = invoiceService.getInvoicesByUserId(userId);

        if (invoices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<InvoiceDTO> invoiceDTOs = invoices.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(invoiceDTOs);
    }

    private InvoiceDTO convertToDTO(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(invoice.getId());
        dto.setTotalPrice(invoice.getTotalPrice());
        dto.setPurchaseDate(invoice.getPurchaseDate());
        dto.setPaymentStatus(invoice.getPaymentStatus());
        dto.setItems(invoice.getItems().stream().map(item -> {
            InvoiceDTO.InvoiceItemDTO itemDTO = new InvoiceDTO.InvoiceItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setTotalPrice(item.getTotalPrice());
            return itemDTO;
        }).collect(Collectors.toSet()));
        return dto;
    }
}
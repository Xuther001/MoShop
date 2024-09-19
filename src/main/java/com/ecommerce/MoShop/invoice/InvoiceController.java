package com.ecommerce.MoShop.invoice;

import com.ecommerce.MoShop.user.User;
import com.ecommerce.MoShop.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{username}")
    public InvoiceDTO getInvoice(@PathVariable String username) {
        List<Invoice> invoices = invoiceService.getInvoicesByUsername(username);
        if (invoices.isEmpty()) {
            throw new IllegalArgumentException("Invoice not found for user: " + username);
        }
        return convertToDTO(invoices.get(0));
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
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setTotalPrice(item.getTotalPrice());
            return itemDTO;
        }).collect(Collectors.toSet()));
        return dto;
    }
}
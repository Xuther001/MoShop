package com.ecommerce.MoShop.invoice;

import com.ecommerce.MoShop.user.User;
import com.ecommerce.MoShop.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return "Purchase Successful";
    }

    @GetMapping("/{invoiceId}")
    public Invoice getInvoice(@PathVariable Long invoiceId) {
        return invoiceService.getInvoiceById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
    }
}
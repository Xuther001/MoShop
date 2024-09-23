package com.ecommerce.MoShop.requestpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestPasswordController {

    private final RequestPasswordService requestPasswordService;

    @Autowired
    public RequestPasswordController(RequestPasswordService requestPasswordService) {
        this.requestPasswordService = requestPasswordService;
    }

    @PostMapping("/request-password")
    public String sendEmail(@RequestParam String email) {
        requestPasswordService.sendPasswordResetLink(email);
        return "Email sent successfully!";
    }
}

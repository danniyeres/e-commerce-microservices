package org.example.paymentservice.controller;

import org.example.paymentservice.model.Payment;
import org.example.paymentservice.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process/{userId}/{orderId}")
    public Payment processPayment(@PathVariable Long userId, @PathVariable Long orderId, @RequestParam double amount) {
        return paymentService.processPayment(userId, orderId, amount);
    }


}

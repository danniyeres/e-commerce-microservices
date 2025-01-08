package org.example.paymentservice.controller;

import org.example.paymentservice.model.Payment;
import org.example.paymentservice.model.PaymentRequest;
import org.example.paymentservice.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process/{userId}/{orderId}")
    public Payment processPayment(@PathVariable Long userId, @PathVariable Long orderId, @RequestBody PaymentRequest paymentRequest) {
        return paymentService.processPayment(userId, orderId, paymentRequest);
    }

    @GetMapping("/get/{paymentId}")
    public Payment getPayment(@PathVariable Long paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    @GetMapping("/getByUser/{userId}")
    public List<Payment> getPaymentsByUserId(@PathVariable Long userId) {
        return paymentService.getPaymentsByUserId(userId);
    }
}

package org.example.paymentservice.service;

import org.example.paymentservice.model.Payment;
import org.example.paymentservice.model.PaymentStatus;
import org.example.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment processPayment(Long userId, Long orderId, double amount) {
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setLocalDateTime(LocalDateTime.now());
        return paymentRepository.save(payment);
    }
}

package org.example.paymentservice.service;

import org.example.paymentservice.kafka.PaymentProducer;
import org.example.paymentservice.kafka.PaymentStatusMessage;
import org.example.paymentservice.model.CardDetails;
import org.example.paymentservice.model.Payment;
import org.example.paymentservice.model.PaymentRequest;
import org.example.paymentservice.model.PaymentStatus;
import org.example.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;

    public PaymentService(PaymentRepository paymentRepository, PaymentProducer paymentProducer) {
        this.paymentRepository = paymentRepository;
        this.paymentProducer = paymentProducer;
    }

    public Payment processPayment(Long userId, Long orderId, PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setOrderId(orderId);
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setLocalDateTime(LocalDateTime.now());
        payment.setCardDetails(new CardDetails(paymentRequest.getCardNumber(), paymentRequest.getExpiry(), paymentRequest.getCvv()));

        boolean paymentSuccess = processWithProvider(userId, orderId, paymentRequest.getAmount());
        if (paymentSuccess) {
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
        }

        payment = paymentRepository.save(payment);

        PaymentStatusMessage message = new PaymentStatusMessage(
                payment.getOrderId(), payment.getId(),  payment.getPaymentStatus().name()
        );
        paymentProducer.sendPaymentStatus( message);

        return payment;
    }

    public boolean processWithProvider(Long userId, Long orderId, double amount) {
        return true;
    }

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.getReferenceById(paymentId);
    }

    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }
}

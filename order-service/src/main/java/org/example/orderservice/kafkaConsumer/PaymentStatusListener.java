package org.example.orderservice.kafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.model.OrderStatus;
import org.example.orderservice.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentStatusListener {

    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    public PaymentStatusListener(ObjectMapper objectMapper, OrderService orderService) {
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

    @KafkaListener(topics = "payment-status", groupId = "order-service-group")
    public void listenPaymentStatus(String message) {
        try {
            log.info("Received payment status: {}", message);

            PaymentStatusMessage paymentStatusMessage = objectMapper.readValue(message, PaymentStatusMessage.class);

            if (paymentStatusMessage.getStatus().equals("SUCCESS")) {
                orderService.updateOrderStatus(paymentStatusMessage.getOrderId(), OrderStatus.CONFIRMED.name());
            } else {
                orderService.updateOrderStatus(paymentStatusMessage.getOrderId(), OrderStatus.FAILED.name());
            }

            log.info("Updated order ID {} to status {}", paymentStatusMessage.getOrderId(), paymentStatusMessage.getStatus());

        } catch (Exception e) {
            log.error("Failed to process payment status message: {}", message, e);
        }
    }

}

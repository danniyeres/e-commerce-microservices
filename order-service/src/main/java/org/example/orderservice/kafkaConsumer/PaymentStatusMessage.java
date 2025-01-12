package org.example.orderservice.kafkaConsumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PaymentStatusMessage {
    private Long orderId;
    private Long paymentId;
    private String status;
}

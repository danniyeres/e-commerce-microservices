package org.example.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentRequest {
    private double amount;
    private String cardNumber;
    private String expiry;
    private String cvv;
}

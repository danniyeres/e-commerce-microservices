package org.example.paymentservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.paymentservice.dto.PaymentDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {
    private static final String TOPIC = "payment-status";
    private final KafkaTemplate<String,Object> kafkaTemplate;


    public PaymentProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    public void sendPaymentStatus( PaymentStatusMessage message){
        kafkaTemplate.send(TOPIC, message);
    }
}

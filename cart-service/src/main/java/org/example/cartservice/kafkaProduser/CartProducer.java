package org.example.cartservice.kafkaProduser;

import org.example.cartservice.dto.CartDto;
import org.example.cartservice.model.Cart;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CartProducer {
    private static final String TOPIC = "cart-events";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CartProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCartEvent(Cart cartDto) {
        kafkaTemplate.send(TOPIC, cartDto);
    }
}

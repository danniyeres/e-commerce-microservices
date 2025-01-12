package org.example.orderservice.kafkaConsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.model.Cart;
import org.example.orderservice.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CartConsumer {
    private static final Logger logger = LoggerFactory.getLogger(CartConsumer.class);

    private final CartRepository cartRepository;

    public CartConsumer(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @KafkaListener(topics = "cart-events", groupId = "order-service-group")
    public void consume(String cartDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("Consuming cart event: {}", cartDto);
        try {
            Cart cart = objectMapper.readValue(cartDto, Cart.class);
            cartRepository.save(cart);
            log.info("Cart: {} added", cart);
        } catch (Exception e) {
            logger.error("Error processing message: {}", cartDto, e);
            throw e;
        }
    }
}

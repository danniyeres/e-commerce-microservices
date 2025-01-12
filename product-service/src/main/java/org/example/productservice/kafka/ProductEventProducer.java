package org.example.productservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.ProductDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductEventProducer {
    private static final String TOPIC = "product-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProductEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductEvent(ProductDto productDto) {
        log.info("Sending product event: {}", productDto);
        kafkaTemplate.send(TOPIC, productDto);
    }
}

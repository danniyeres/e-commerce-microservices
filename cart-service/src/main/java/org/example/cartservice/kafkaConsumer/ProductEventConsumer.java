package org.example.cartservice.kafkaConsumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.cartservice.model.Product;
import org.example.cartservice.repository.ProductRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductEventConsumer {
    private final ProductRepository productRepository;

    public ProductEventConsumer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @KafkaListener(topics = "product-events", groupId = "cart-service-group")
    public void consume(String productDto) {
        log.info("Consuming product event: {}", productDto);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Product product = objectMapper.readValue(productDto, Product.class);
            productRepository.save(product);
            log.info("Product: {} added", product);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize product", e);
        }
    }
}

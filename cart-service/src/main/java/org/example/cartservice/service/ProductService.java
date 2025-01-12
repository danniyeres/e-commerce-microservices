package org.example.cartservice.service;

import org.example.cartservice.model.Product;
import org.example.cartservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }
}

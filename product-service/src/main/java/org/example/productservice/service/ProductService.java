package org.example.productservice.service;


import org.example.productservice.dto.ProductDto;
import org.example.productservice.model.Product;
import org.example.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductDto addProduct(ProductDto productDto) {

        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .build();

        Product savedProduct = productRepository.save(product);

        return ProductDto.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .price(savedProduct.getPrice())
                .quantity(savedProduct.getQuantity())
                .build();
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    public ProductDto getProductByName(String name) {
        Product product = productRepository.findByName(name);
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        Product updatedProduct = productRepository.save(product);
        return ProductDto.builder()
                .id(updatedProduct.getId())
                .name(updatedProduct.getName())
                .price(updatedProduct.getPrice())
                .quantity(updatedProduct.getQuantity())
                .build();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

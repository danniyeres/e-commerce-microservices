package org.example.productservice.service;


import org.example.productservice.dto.ProductDto;
import org.example.productservice.kafka.ProductEventProducer;
import org.example.productservice.model.Product;
import org.example.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductEventProducer productEventProducer;

    public ProductService(ProductRepository productRepository, ProductEventProducer productEventProducer) {
        this.productRepository = productRepository;
        this.productEventProducer = productEventProducer;
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public ProductDto addProduct(ProductDto productDto) {

        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .build();

        Product savedProduct = productRepository.save(product);



        ProductDto getProduct =  ProductDto.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .price(savedProduct.getPrice())
                .quantity(savedProduct.getQuantity())
                .build();

        productEventProducer.sendProductEvent(getProduct);

        return getProduct;

    }

    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> ProductDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .build())
                .orElseThrow(() -> new RuntimeException("Product not found"));
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

        ProductDto getProduct = ProductDto.builder()
                .id(updatedProduct.getId())
                .name(updatedProduct.getName())
                .price(updatedProduct.getPrice())
                .quantity(updatedProduct.getQuantity())
                .build();
        productEventProducer.sendProductEvent(getProduct);
        return getProduct;

    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

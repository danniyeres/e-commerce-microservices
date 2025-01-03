package org.example.cartservice.model;

import org.example.productservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/products/get/{id}")
    Product getProductById(@PathVariable Long id);
}

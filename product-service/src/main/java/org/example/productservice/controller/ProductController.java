package org.example.productservice.controller;

import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import org.example.productservice.dto.ProductDto;
import org.example.productservice.model.Product;
import org.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @GetMapping("/get/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/getByName/{name}")
    public ProductDto getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @PutMapping("/update/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}

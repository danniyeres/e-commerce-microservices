package org.example.orderservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.example.cartservice.model.Cart;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service", url = "http://localhost:8084/")
public interface CartClient {
    @GetMapping("/cart/get/{userId}")
    Cart getCartByUserId(@PathVariable Long userId);
}

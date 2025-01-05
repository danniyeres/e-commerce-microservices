package org.example.cartservice.controller;

import org.example.cartservice.model.Cart;
import org.example.cartservice.model.CartItem;
import org.example.cartservice.model.CartItemRequest;
import org.example.cartservice.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add/{userId}")
    public Cart addItemToCart (@PathVariable Long userId, @RequestBody CartItemRequest cartItemRequest) {
        return cartService.addItemToCart(userId, cartItemRequest.getProductId(), cartItemRequest.getQuantity());
    }

    @DeleteMapping("/remove/{userId}/{productId}")
    public void removeItemFromCart (@PathVariable Long userId, @PathVariable Long productId) {
        cartService.removeItemFromCart(userId, productId);
    }

    @DeleteMapping("/clear/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }


    @GetMapping("/get/{userId}")
    public Cart getCart (@PathVariable Long userId) {
        System.out.println("get cart");
        return cartService.getCartByUserId(userId);
    }
}

package org.example.cartservice.service;


import org.example.cartservice.model.Cart;
import org.example.cartservice.feignClient.ProductClient;
import org.example.cartservice.repository.CartRepository;
import org.example.productservice.model.Product;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductClient productClient;


    public CartService(CartRepository cartRepository, ProductClient productClient) {
        this.cartRepository = cartRepository;
        this.productClient = productClient;
    }

    public Cart getCartByUserId(Long userId) {
        if (!cartRepository.existsByUserId(userId)) {
            throw new RuntimeException("Cart not found");
        }
        return cartRepository.findByUserId(userId);
    }

    public Cart addItemToCart(Long userId, Long productId, int quantity) {

        Product product = productClient.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
        }

        cart.addItem(productId, quantity, product.getPrice());
        return cartRepository.save(cart);
    }

    public void removeItemFromCart(Long userId, Long productId) {
        if (!cartRepository.existsByUserId(userId)) {
            throw new RuntimeException("Cart not found");
        }
        Cart cart = cartRepository.findByUserId(userId);
        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(Long userId) {
        if (!cartRepository.existsByUserId(userId)) {
            throw new RuntimeException("Cart not found");
        }
        Cart cart = cartRepository.findByUserId(userId);
        cart.clearCart();
        cartRepository.save(cart);
    }
}

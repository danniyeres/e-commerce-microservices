package org.example.cartservice.service;


import org.example.cartservice.dto.CartDto;
import org.example.cartservice.kafkaProduser.CartProducer;
import org.example.cartservice.model.Cart;
import org.example.cartservice.model.Product;
import org.example.cartservice.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartProducer cartProducer;

    public CartService(CartRepository cartRepository, ProductService productService, CartProducer cartProducer) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartProducer = cartProducer;
    }

    public Cart getCartByUserId(Long userId) {
        if (!cartRepository.existsByUserId(userId)) {
            throw new RuntimeException("Cart not found");
        }
        return cartRepository.findByUserId(userId);
    }

    public Cart addItemToCart(Long userId, Long productId, int quantity) {

        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
        }
        cart.addItem(productId, quantity, product.getPrice());

        sendCartToOrderService(userId);
        return cartRepository.save(cart);
    }

    public void removeItemFromCart(Long userId, Long productId) {
        if (!cartRepository.existsByUserId(userId)) {
            throw new RuntimeException("Cart not found");
        }
        Cart cart = cartRepository.findByUserId(userId);
        cart.removeItem(productId);
        sendCartToOrderService(userId);
        cartRepository.save(cart);
    }

    public void clearCart(Long userId) {
        if (!cartRepository.existsByUserId(userId)) {
            throw new RuntimeException("Cart not found");
        }
        Cart cart = cartRepository.findByUserId(userId);
        cart.clearCart();
        sendCartToOrderService(userId);
        cartRepository.save(cart);
    }

    public void sendCartToOrderService(Long userId) {
        if (!cartRepository.existsByUserId(userId)) {
            throw new RuntimeException("Cart not found");
        }
        Cart cart = cartRepository.findByUserId(userId);
        cartProducer.sendCartEvent(cart);
    }
}

package org.example.orderservice.service;

import org.example.orderservice.model.Cart;
import org.example.orderservice.model.CartItem;
import org.example.orderservice.model.Order;
import org.example.orderservice.model.OrderItem;
import org.example.orderservice.repository.CartRepository;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    public Order createOrder(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<OrderItem> items = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .productId(cartItem.getProductId())
                    .quantity(cartItem.getQuantity())
                    .build();
            items.add(orderItem);
        }

        double totalPrice = cartRepository.findByUserId(userId).getTotalPrice();

        Order order = Order.builder()
                .userId(userId)
                .items(items)
                .totalPrice(totalPrice)
                .status("CREATED")
                .build();
        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<Order> getOrderByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}

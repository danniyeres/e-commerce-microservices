package org.example.orderservice.repository;

import org.example.orderservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}

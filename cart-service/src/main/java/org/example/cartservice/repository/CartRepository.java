package org.example.cartservice.repository;

import org.example.cartservice.dto.CartDto;
import org.example.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{
    Cart findByUserId(Long userId);
    CartDto findCartByUserId(Long userId);
    boolean existsByUserId(Long userId);
}

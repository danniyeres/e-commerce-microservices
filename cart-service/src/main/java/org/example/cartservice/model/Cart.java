package org.example.cartservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {

    @Id
    private Long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public void addItem(Long productId, int quantity) {
        for (CartItem cartItem : items) {
            if (cartItem.getProductId().equals(productId)) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return;
            }
        }

        CartItem item = new CartItem();
        item.setProductId(productId);
        item.setQuantity(quantity);
        item.setCart(this);
        items.add(item);
    }

    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
    }

    public void clearCart() {
        for (CartItem item : items) {
            item.setCart(null);
        }
        items.clear();
    }
}

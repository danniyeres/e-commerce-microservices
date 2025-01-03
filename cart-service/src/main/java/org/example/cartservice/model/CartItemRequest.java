package org.example.cartservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {
    private Long productId;
    private int quantity;
}

package com.omid.orderservice;

import lombok.Data;

@Data
public class OrderItem {
    private Food food;
    private Integer quantity;
    private Double totalPrice;
}

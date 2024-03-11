package com.omid.restaurantclientservice;

import lombok.Data;

@Data
public class OrderItem {
    private Food food;
    private Integer quantity;
    private Double totalPrice;
}

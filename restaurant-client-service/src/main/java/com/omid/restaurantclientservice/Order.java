package com.omid.restaurantclientservice;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private String id;
    private String customerFullName;
    private String orderNumber;
    private List<OrderItem> orderItemList;
    private Double finalPrice;
    private LocalDateTime orderDate;
}

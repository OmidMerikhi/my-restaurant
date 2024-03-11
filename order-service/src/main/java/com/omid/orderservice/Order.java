package com.omid.orderservice;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    @Id
    private String id;
    private String customerFullName;
    private String orderNumber;
    private List<OrderItem> orderItemList;
    private Double finalPrice;
    private LocalDateTime orderDate;

}

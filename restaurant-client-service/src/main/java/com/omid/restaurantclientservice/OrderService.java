package com.omid.restaurantclientservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderClientService orderClientService;

    public List<Order> loadAll() {
        return orderClientService.loadAllOrders();
    }

    public Order create(List<OrderItem> items, String name) {
        return orderClientService.createOrder(items, name);
    }

    public List<Order> loadByDate(Date date) {
        return orderClientService.loadOrderByDate(date);
    }
}

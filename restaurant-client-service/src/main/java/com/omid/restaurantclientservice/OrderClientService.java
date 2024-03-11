package com.omid.restaurantclientservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "order-service", path = "/api/orders")
public interface OrderClientService {
    @GetMapping
    List<Order> loadAllOrders();

    @PostMapping
    Order createOrder(@RequestBody List<OrderItem> items, @RequestParam("customer-name") String name);

    @GetMapping("/foods")
    public List<Food> loadAllFoods();

    @GetMapping("/by-date")
    public List<Order> loadOrderByDate(@RequestBody Date date);
}

package com.omid.orderservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/foods")
    public List<Food> loadAllFood() {
        return orderService.loadAll();
    }

    @GetMapping
    public List<Order> loadAllOrders() {
        return orderService.loadAllOrders();
    }

    @GetMapping("/by-date")
    public List<Order> loadByDate(@RequestBody Date date) {
        return orderService.loadByDate(date);
    }

    @PostMapping
    public Order createOrder(@RequestBody List<OrderItem> items, @RequestParam("customer-name") String name) {
        return orderService.createOrder(items, name);
    }
}

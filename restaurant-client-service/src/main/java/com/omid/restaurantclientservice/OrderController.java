package com.omid.restaurantclientservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client-order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<Order> loadAll() {
        return orderService.loadAll();
    }

    @GetMapping("/by-date")
    public List<Order> loadByDate(@RequestBody Date date) {
        return orderService.loadByDate(date);
    }

    @PostMapping
    public Order create(@RequestBody List<OrderItem> items, @RequestParam("name") String name) {
        return orderService.create(items, name);
    }
}

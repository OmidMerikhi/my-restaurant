package com.omid.restaurantclientservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "food-service", path = "/api/foods")
public interface FoodClientService {
    @GetMapping
    List<Food> loadAllFood();

    @GetMapping("/{id}")
    Food loadOneFood(@PathVariable("id") String id);

    @PostMapping
    Food createFood(@RequestBody Food food);

    @PutMapping("/{id}")
    Food updateFood(@PathVariable("id") String id, @RequestBody Food food);

    @GetMapping("/search")
    Page<Food> searchFood(FoodSearchFilterClient searchFilterClient, Pageable pageable);
}

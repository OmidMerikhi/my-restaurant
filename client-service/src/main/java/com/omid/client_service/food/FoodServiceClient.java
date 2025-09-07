package com.omid.client_service.food;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "food-service", path = "/api/foods", fallback = FoodServiceFallback.class)
public interface FoodServiceClient {
    @GetMapping
    List<Food> loadAllFoods();
}

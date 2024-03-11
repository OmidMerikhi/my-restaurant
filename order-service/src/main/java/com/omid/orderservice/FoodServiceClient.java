package com.omid.orderservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@FeignClient(value = "food-service", url = "http://localhost:8080/api/foods")
public interface FoodServiceClient {
    @GetMapping()
    List<Food> loadAllFoods();
}

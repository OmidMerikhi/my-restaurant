package com.omid.client_service.food;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods")
public class FoodClientController {
    private final FoodServiceClient foodServiceClient;

    @GetMapping
    public List<Food> loadAll() {
        return foodServiceClient.loadAllFoods();
    }
}

package com.omid.client_service.food;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceFallback implements FoodServiceClient {
    @Override
    public List<Food> loadAllFoods() {
        return new ArrayList<>();
    }
}

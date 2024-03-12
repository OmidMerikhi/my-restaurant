package com.omid.restaurantclientservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FoodFallback implements FoodClientService {
    @Override
    public List<Food> loadAllFood() {
        return new ArrayList<>();
    }

    @Override
    public Food loadOneFood(String id) {
        return new Food();
    }

    @Override
    public Food createFood(Food food) {
        return new Food();
    }

    @Override
    public Food updateFood(String id, Food food) {
        return new Food();
    }

    @Override
    public Page<Food> searchFood(FoodSearchFilterClient searchFilterClient, Pageable pageable) {
        return null;
    }
}

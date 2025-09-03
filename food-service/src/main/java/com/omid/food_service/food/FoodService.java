package com.omid.food_service.food;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    public Food create(Food food) {
        return foodRepository.save(food);
    }

    public Food loadOne(Long id) {
        return foodRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("food not found!"));
    }

    public List<Food> loadAll() {
        return foodRepository.findAll();
    }

    public void update(Long id, FoodDto foodDto) {
        Food dbFood = loadOne(id);
        foodMapper.updateFoodFromDto(foodDto, dbFood);
        foodRepository.save(dbFood);
    }
}

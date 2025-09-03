package com.omid.food_service.food;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;
    private final FoodMapper foodMapper;

    @GetMapping
    public List<FoodDto> loadAll() {
        return foodMapper.toViewModel(foodService.loadAll());
    }

    @GetMapping("/{id}")
    public FoodDto loadOne(@PathVariable("id") Long id) {
        return foodMapper.toViewModel(foodService.loadOne(id));
    }

    @PostMapping
    public FoodDto create(@RequestBody Food food) {
        return foodMapper.toViewModel(foodService.create(food));
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody FoodDto foodDto) {
        foodService.update(id, foodDto);
    }


}

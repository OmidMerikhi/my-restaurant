package com.omid.foodservice.food;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;
    private final FoodMapper foodMapper;
    private final Environment env;


    @GetMapping
    public List<FoodDto> loadAll() {
        System.out.println(env.getProperty("local.server.port"));
        return foodMapper.toViewModel(foodService.loadAllFood());
    }

    @GetMapping("/{id}")
    public FoodDto loadOne(@PathVariable("id") String id) {
        return foodMapper.toViewModel(foodService.loadOneFood(id));
    }

    @GetMapping("/search")
    public Page<Food> search(FoodSearchFilter searchFilter, Pageable pageable) {
        return foodService.searchFood(searchFilter, pageable);
    }

    @PostMapping
    public FoodDto create(@RequestBody FoodDto foodDto) {
        return foodMapper.toViewModel(foodService.createFood(foodMapper.toEntity(foodDto)));
    }

    @PutMapping("/{id}")
    public FoodDto update(@PathVariable("id") String id, @RequestBody FoodDto foodDto) {
        return foodMapper.toViewModel(foodService.updateFood(id, foodMapper.toEntity(foodDto)));
    }

}

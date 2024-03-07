package com.omid.foodservice.food;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping
    public List<Food> loadAll(){
        return foodService.loadAllFood();
    }

    @GetMapping("/{id}")
    public Food loadOne(@PathVariable("id") String id){
        return foodService.loadOneFood(id);
    }

    @GetMapping("/search")
    public Page<Food> search(FoodSearchFilter searchFilter, Pageable pageable){
        return foodService.searchFood(searchFilter,pageable);
    }

    @PostMapping
    public Food create(@RequestBody Food food){
        return foodService.createFood(food);
    }

    @PutMapping("/{id}")
    public Food update(@PathVariable("id") String id,@RequestBody Food food){
        return foodService.updateFood(id,food);
    }

}
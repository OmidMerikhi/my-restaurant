package com.omid.restaurantclientservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client-food")
@RequiredArgsConstructor
public class FoodController {
    private final FoodClientService foodClientService;

    @GetMapping
    public List<Food> loadAll() {
        return foodClientService.loadAllFood();
    }

    @GetMapping("/{id}")
    public Food loadOne(@PathVariable("id") String id) {
        return foodClientService.loadOneFood(id);
    }

    @GetMapping("/search")
    public Page<Food> search(FoodSearchFilterClient searchFilterClient, Pageable pageable) {
        return foodClientService.searchFood(searchFilterClient, pageable);
    }

    @PostMapping
    public Food create(@RequestBody Food food) {
        return foodClientService.createFood(food);
    }

    @PutMapping("/{id}")
    public Food update(@PathVariable("id") String id, @RequestBody Food food) {
        return foodClientService.updateFood(id, food);
    }
}

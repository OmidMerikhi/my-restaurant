package com.omid.employeeservice.chef;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chefs")
@RequiredArgsConstructor
public class ChefController {
    private final ChefService chefService;

    @GetMapping
    public List<Chef> loadAll() {
        return chefService.loadAll();
    }

    @GetMapping("/{id}")
    public Chef loadOne(@PathVariable("id") String id) {
        return chefService.loadOneById(id);
    }

    @PostMapping
    public Chef create(@RequestBody Chef chef) {
        return chefService.create(chef);
    }

    @PutMapping("/{id}")
    public Chef update(@PathVariable("id") String id, @RequestBody Chef chef) {
        return chefService.update(id, chef);
    }

    @GetMapping("/search")
    public List<Chef> search(@RequestParam("value") String value) {
        return chefService.search(value);
    }
}

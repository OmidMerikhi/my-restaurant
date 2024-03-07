package com.omid.foodservice.food;

import lombok.Data;

@Data
public class FoodSearchFilter {
    private String title;
    private String material;
    private String size;
    private double price;
    private Boolean active;
    private Category category;
}

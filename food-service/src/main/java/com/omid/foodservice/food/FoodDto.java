package com.omid.foodservice.food;

import lombok.Data;

@Data
public class FoodDto {
    private String title;
    private String material;
    private double price;
    private String size;
    private Boolean active;
    private Category category;
}

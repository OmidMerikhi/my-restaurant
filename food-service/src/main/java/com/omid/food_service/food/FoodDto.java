package com.omid.food_service.food;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FoodDto {

    private Long id;

    private String title;

    private FoodType foodType;

    private String description;
}

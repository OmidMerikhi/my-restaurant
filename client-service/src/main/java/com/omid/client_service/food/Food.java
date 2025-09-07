package com.omid.client_service.food;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Food {
    private Long id;

    private String title;

    private String description;

    private FoodType foodType;
}

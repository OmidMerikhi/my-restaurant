package com.omid.restaurantclientservice;

import lombok.Data;

@Data
public class FoodSearchFilterClient {
    private String title;
    private String material;
    private String size;
    private double price;
    private Boolean active;
    private Category category;
}

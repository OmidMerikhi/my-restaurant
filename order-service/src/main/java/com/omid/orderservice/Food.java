package com.omid.orderservice;

import lombok.Data;

@Data
public class Food {
    private String title;
    private String material;
    private double price;
    private String size;
    private Boolean active;
}

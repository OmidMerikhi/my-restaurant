package com.omid.restaurantclientservice;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Food {
    @Id
    private String id;
    private String title;
    private String material;
    private double price;
    private String size;
    private Boolean active;
    @Enumerated(EnumType.STRING)
    private Category category;

    public Food() {
        setId(UUID.randomUUID().toString());
    }
}

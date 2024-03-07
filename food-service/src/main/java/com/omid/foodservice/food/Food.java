package com.omid.foodservice.food;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import org.yaml.snakeyaml.events.Event;

import java.util.UUID;

@Entity
@Data
public class Food {
    @Id
    private String Id;
    private String title;
    private String material;
    private double price;
    private String size;
    private Boolean active;
    @Enumerated(EnumType.STRING)
    private Category category;

    public Food(){
        setId(UUID.randomUUID().toString());
    }

}

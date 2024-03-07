package com.omid.foodservice.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food,String>, QuerydslPredicateExecutor<Food> {
    Food getFoodById(String id);
}

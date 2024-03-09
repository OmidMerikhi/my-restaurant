package com.omid.foodservice.food;

import com.omid.foodservice.tools.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodMapper extends EntityMapper<FoodDto,Food> {
    FoodDto toViewModel(Food food);
    Food toEntity(FoodDto viewModel);

}

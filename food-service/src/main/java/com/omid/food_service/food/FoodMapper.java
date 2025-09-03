package com.omid.food_service.food;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodMapper extends EntityMapper<FoodDto, Food> {
    FoodDto toViewModel(Food entity);

    Food toEntity(FoodDto viewModel);

//    List<Food> toEntityList(List<FoodDto> viewModelList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFoodFromDto(FoodDto dto, @MappingTarget Food entity);
}

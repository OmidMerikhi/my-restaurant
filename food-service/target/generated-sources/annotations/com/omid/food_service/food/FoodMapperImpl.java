package com.omid.food_service.food;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-03T12:08:03+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Ubuntu)"
)
@Component
public class FoodMapperImpl implements FoodMapper {

    @Override
    public List<FoodDto> toViewModel(List<Food> entity) {
        if ( entity == null ) {
            return null;
        }

        List<FoodDto> list = new ArrayList<FoodDto>( entity.size() );
        for ( Food food : entity ) {
            list.add( toViewModel( food ) );
        }

        return list;
    }

    @Override
    public List<Food> toEntity(List<FoodDto> viewModel) {
        if ( viewModel == null ) {
            return null;
        }

        List<Food> list = new ArrayList<Food>( viewModel.size() );
        for ( FoodDto foodDto : viewModel ) {
            list.add( toEntity( foodDto ) );
        }

        return list;
    }

    @Override
    public FoodDto toViewModel(Food entity) {
        if ( entity == null ) {
            return null;
        }

        FoodDto foodDto = new FoodDto();

        foodDto.setId( entity.getId() );
        foodDto.setTitle( entity.getTitle() );
        foodDto.setFoodType( entity.getFoodType() );
        foodDto.setDescription( entity.getDescription() );

        return foodDto;
    }

    @Override
    public Food toEntity(FoodDto viewModel) {
        if ( viewModel == null ) {
            return null;
        }

        Food food = new Food();

        food.setId( viewModel.getId() );
        food.setTitle( viewModel.getTitle() );
        food.setFoodType( viewModel.getFoodType() );
        food.setDescription( viewModel.getDescription() );

        return food;
    }

    @Override
    public void updateFoodFromDto(FoodDto dto, Food entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getFoodType() != null ) {
            entity.setFoodType( dto.getFoodType() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
    }
}

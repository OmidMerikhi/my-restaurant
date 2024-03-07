package com.omid.foodservice.food;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final QFood qFood=QFood.food;

    public Food createFood(Food food){
        return foodRepository.save(food);
    }

    public Food loadOneFood(String id){
        return foodRepository.getFoodById(id);
    }

    public Food updateFood(String id, Food food){
        Food dbFood=loadOneFood(id);
        if(food.getTitle()!=null){
            dbFood.setTitle(food.getTitle());
        }
        if(food.getMaterial()!=null){
            dbFood.setMaterial(food.getMaterial());
        }
        if(food.getPrice()!=0){
            dbFood.setPrice(food.getPrice());
        }
        if(food.getSize()!=null){
            dbFood.setSize(food.getSize());
        }
        if(food.getActive()!=null){
            dbFood.setActive(food.getActive());
        }
        if(food.getCategory()!=null){
            dbFood.setCategory(food.getCategory());
        }

        return foodRepository.saveAndFlush(dbFood);
    }

    public List<Food> loadAllFood(){
        return foodRepository.findAll();
    }

    public Page<Food> searchFood(FoodSearchFilter foodSearchFilter, Pageable pageable){
        BooleanBuilder predicate=new BooleanBuilder();

        if(foodSearchFilter.getTitle()!=null){
            predicate.and(qFood.title.contains(foodSearchFilter.getTitle()));
        }
        if(foodSearchFilter.getMaterial()!=null){
            predicate.and(qFood.material.contains(foodSearchFilter.getMaterial()));
        }
        if(foodSearchFilter.getSize()!=null){
            predicate.and(qFood.size.contains(foodSearchFilter.getSize()));
        }
        if(foodSearchFilter.getActive()!=null){
            predicate.and(qFood.active.eq(foodSearchFilter.getActive()));
        }
        if(foodSearchFilter.getPrice()!=0){
            predicate.and(qFood.price.eq(foodSearchFilter.getPrice()));
        }
        if(foodSearchFilter.getCategory()!=null){
            predicate.and(qFood.category.stringValue().eq(foodSearchFilter.getCategory().getValue()));
        }

        return foodRepository.findAll(predicate,pageable);
    }
}

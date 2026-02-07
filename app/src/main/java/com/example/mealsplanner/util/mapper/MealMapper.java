package com.example.mealsplanner.util.mapper;

import com.example.mealsplanner.data.model.domain.Meal;
import com.example.mealsplanner.data.model.dto.MealDto;
import com.example.mealsplanner.data.model.entity.FavoriteMealEntity;
import com.example.mealsplanner.data.model.entity.PlannedMealEntity;

public class MealMapper {
    public static Meal toDomain(MealDto dto) {
        return new Meal(
                dto.getId(),
                dto.getName(),
                dto.getCategory(),
                dto.getArea(),
                dto.getInstructions(),
                dto.getImage(),
                dto.getIngredients(),
                dto.getMeasures(),
                dto.getYoutube()
        );

    }

    public static Meal fromEntity(FavoriteMealEntity entity) {
        return new Meal(
                entity.getId(),
                entity.getName(),
                entity.getCategory(),
                entity.getArea(),
                entity.getInstructions(),
                entity.getImage(),
                entity.getIngredients(),
                entity.getMeasures(),
                entity.getYoutube());
    }

    public static Meal fromEntity(PlannedMealEntity entity) {
        return new Meal(
                entity.getMealId(),
                entity.getName(),
                entity.getCategory(),
                entity.getArea(),
                entity.getInstructions(),
                entity.getImage(),
                entity.getIngredients(),
                entity.getMeasures(),
                entity.getYoutube());
    }
}

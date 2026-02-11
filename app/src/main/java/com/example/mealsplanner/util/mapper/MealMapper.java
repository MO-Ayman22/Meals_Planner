package com.example.mealsplanner.util.mapper;

import com.example.mealsplanner.data.model.domain.Meal;
import com.example.mealsplanner.data.model.domain.MealPreview;
import com.example.mealsplanner.data.model.dto.MealDto;
import com.example.mealsplanner.data.model.dto.MealPreviewDto;
import com.example.mealsplanner.data.model.entity.FavoriteMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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


    public static Meal fromDto(MealDto mealDto) {
        return new Meal(mealDto.getId(),
                mealDto.getName(),
                mealDto.getCategory(),
                mealDto.getArea(),
                mealDto.getInstructions(),
                mealDto.getImage(),
                mealDto.getIngredients(),
                mealDto.getMeasures(),
                mealDto.getYoutube());
    }

    public static List<Meal> fromDtos(List<MealDto> dtos) {
        return Observable.fromIterable(dtos)
                .subscribeOn(Schedulers.computation())
                .map(MealMapper::fromDto)
                .toList()
                .blockingGet();
    }

    public static List<MealPreview> fromPreviewDtos(List<MealPreviewDto> dtos) {
        return Observable.fromIterable(dtos)
                .subscribeOn(Schedulers.computation())
                .map(MealMapper::fromPreviewDto)
                .toList()
                .blockingGet();
    }

    public static MealPreview fromPreviewDto(MealPreviewDto dto) {
        return new MealPreview(
                dto.getId(),
                dto.getName(),
                dto.getImage()
        );
    }

    public static List<Meal> fromFavEntities(List<FavoriteMealEntity> favoriteMealEntities) {
        return Observable.fromIterable(favoriteMealEntities)
                .subscribeOn(Schedulers.computation())
                .map(MealMapper::fromFavEntity)
                .toList()
                .blockingGet();

    }

    private static Meal fromFavEntity(FavoriteMealEntity favoriteMealEntity) {
        return new Meal(
                favoriteMealEntity.getId(),
                favoriteMealEntity.getName(),
                favoriteMealEntity.getCategory(),
                favoriteMealEntity.getArea(),
                favoriteMealEntity.getInstructions(),
                favoriteMealEntity.getImage(),
                favoriteMealEntity.getIngredients(),
                favoriteMealEntity.getMeasures(),
                favoriteMealEntity.getYoutube()
        );
    }

    public static FavoriteMealEntity toFavEntity(Meal meal, String userId) {
        return new FavoriteMealEntity(
                userId,
                meal.getId(),
                meal.getName(),
                meal.getCategory(),
                meal.getArea(),
                meal.getInstructions(),
                meal.getImage(),
                meal.getIngredients(),
                meal.getMeasures(),
                meal.getYoutube()
        );
    }
}

package com.example.mealsplanner.data.source.remote.mealsource;

import com.example.mealsplanner.data.domain.dto.AreaDto;
import com.example.mealsplanner.data.domain.dto.CategoryDto;
import com.example.mealsplanner.data.domain.dto.MealDto;
import com.example.mealsplanner.data.domain.dto.MealPreviewDto;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealsRemoteDataSource {
    Single<MealDto> getRandomMeal();

    Single<List<CategoryDto>> getCategories();

    Single<List<MealDto>> getMealsByName(String query);

    Single<List<MealDto>> searchMealsByFirstLetter(char letter);

    Single<List<MealPreviewDto>> filterByArea(String area);

    Single<List<MealPreviewDto>> filterByCategory(String category);

    Single<MealDto> getMealById(String id);

    Single<List<AreaDto>> getAreas();
}

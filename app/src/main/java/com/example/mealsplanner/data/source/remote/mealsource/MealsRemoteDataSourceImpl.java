package com.example.mealsplanner.data.source.remote.mealsource;

import com.example.mealsplanner.data.domain.dto.AreaDto;
import com.example.mealsplanner.data.domain.dto.CategoryDto;
import com.example.mealsplanner.data.domain.dto.MealDto;
import com.example.mealsplanner.data.domain.dto.MealPreviewDto;
import com.example.mealsplanner.data.domain.wrapper.AreaListResponse;
import com.example.mealsplanner.data.domain.wrapper.CategoriesResponse;
import com.example.mealsplanner.data.domain.wrapper.MealPreviewListResponse;
import com.example.mealsplanner.data.domain.wrapper.MealsResponse;
import com.example.mealsplanner.data.source.remote.api.MealsApiService;

import java.util.List;
import java.util.NoSuchElementException;

import io.reactivex.rxjava3.core.Single;

public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource {

    private final MealsApiService api;

    public MealsRemoteDataSourceImpl(MealsApiService api) {
        this.api = api;
    }

    public Single<MealDto> getRandomMeal() {
        return api.getRandomMeal()
                .map(response -> {
                    if (response.getMeals() == null || response.getMeals().isEmpty()) {
                        throw new NoSuchElementException("No meal found");
                    }
                    return response.getMeals().get(0);
                });
    }

    public Single<List<CategoryDto>> getCategories() {
        return api.getCategories()
                .map(CategoriesResponse::getCategories);
    }

    public Single<List<AreaDto>> getAreas() {
        return api.getAreas()
                .map(AreaListResponse::getAreas);
    }

    public Single<List<MealDto>> getMealsByName(String query) {
        return api.searchMealsByName(query)
                .map(MealsResponse::getMeals);
    }

    public Single<List<MealDto>> searchMealsByFirstLetter(char letter) {
        return api.searchMealsByFirstLetter(letter)
                .map(MealsResponse::getMeals);
    }

    public Single<List<MealPreviewDto>> filterByArea(String area) {
        return api.filterByArea(area)
                .map(MealPreviewListResponse::getMeals);
    }

    public Single<List<MealPreviewDto>> filterByCategory(String category) {
        return api.filterByCategory(category)
                .map(MealPreviewListResponse::getMeals);
    }

    @Override
    public Single<MealDto> getMealById(String id) {
        return api.getMealById(id)
                .map(response -> {
                    if (response.getMeals() == null || response.getMeals().isEmpty()) {
                        throw new NoSuchElementException("No meal found");
                    }
                    return response.getMeals().get(0);
                });
    }

}


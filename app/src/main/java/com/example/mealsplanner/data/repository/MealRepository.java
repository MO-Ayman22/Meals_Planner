package com.example.mealsplanner.data.repository;

import com.example.mealsplanner.core.BaseApplication;
import com.example.mealsplanner.data.domain.model.Meal;
import com.example.mealsplanner.data.domain.model.MealPreview;
import com.example.mealsplanner.data.source.remote.mealsource.MealsRemoteDataSource;
import com.example.mealsplanner.util.mapper.MealMapper;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class MealRepository {

    private final MealsRemoteDataSource remote;

    public MealRepository(MealsRemoteDataSource remote) {
        this.remote = remote;
    }

    public Single<Meal> getRandomMeal() {
        if (BaseApplication.getInstance().session().getRandomMeal() != null) {
            return getMealById(BaseApplication.getInstance().session().getRandomMeal());
        }
        return remote.getRandomMeal()
                .map(MealMapper::fromDto);
    }

    public Single<Meal> getMealById(String id) {
        return remote.getMealById(id)
                .map(MealMapper::fromDto);
    }

    public Single<List<Meal>> searchByName(String query) {
        return remote.getMealsByName(query)
                .map(dtos -> {
                    if (dtos == null) return Collections.emptyList();
                    return MealMapper.fromDtos(dtos);
                });
    }

    public Single<List<Meal>> searchByFirstLetter(char letter) {
        return remote.searchMealsByFirstLetter(letter)
                .map(dtos -> {
                    if (dtos == null) return Collections.emptyList();
                    return MealMapper.fromDtos(dtos);
                });
    }

    public Single<List<MealPreview>> filterByArea(String area) {
        return remote.filterByArea(area)
                .map(dtos -> {
                    if (dtos == null) return Collections.emptyList();
                    return MealMapper.fromPreviewDtos(dtos);
                });
    }

    public Single<List<MealPreview>> filterByCategory(String category) {
        return remote.filterByCategory(category)
                .map(dtos -> {
                    if (dtos == null) return Collections.emptyList();
                    return MealMapper.fromPreviewDtos(dtos);
                });


    }


}

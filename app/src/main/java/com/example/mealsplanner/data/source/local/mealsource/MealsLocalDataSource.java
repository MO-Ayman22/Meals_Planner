package com.example.mealsplanner.data.source.local.mealsource;

import com.example.mealsplanner.data.model.entity.AreaEntity;
import com.example.mealsplanner.data.model.entity.CategoryEntity;
import com.example.mealsplanner.data.model.entity.FavoriteMealEntity;
import com.example.mealsplanner.data.model.entity.PlannedMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealsLocalDataSource {

    Single<List<AreaEntity>> getAreas();

    Completable insertAreas(List<AreaEntity> areas);

    Completable clearAreas();

    Single<List<CategoryEntity>> getCategories();

    Completable insertCategories(List<CategoryEntity> categories);

    Completable clearCategories();

    Flowable<List<PlannedMealEntity>> getMealsByDay(String day);

    Completable insertPlannedMeal(PlannedMealEntity meal);

    Completable deletePlannedMeal(int planId);

    Flowable<List<FavoriteMealEntity>> getFavoriteMeals();

    Completable insertFavoriteMeal(FavoriteMealEntity meal);

    Completable deleteFavoriteMeal(String mealId);


}

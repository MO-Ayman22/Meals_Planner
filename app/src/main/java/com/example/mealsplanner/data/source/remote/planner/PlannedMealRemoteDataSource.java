package com.example.mealsplanner.data.source.remote.planner;

import com.example.mealsplanner.data.domain.entity.PlannedMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface PlannedMealRemoteDataSource {

    Completable insertPlannedMeal(String userId, PlannedMealEntity meal);

    Completable deletePlannedMeal(String userId, PlannedMealEntity meal);

    Single<List<PlannedMealEntity>> getMealsByDay(String userId, String day);
}

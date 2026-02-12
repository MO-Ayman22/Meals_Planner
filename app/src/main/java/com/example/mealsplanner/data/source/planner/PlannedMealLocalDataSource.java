package com.example.mealsplanner.data.source.planner;

import com.example.mealsplanner.data.domain.entity.PlannedMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface PlannedMealLocalDataSource {

    Completable insertPlannedMeal(PlannedMealEntity meal);

    Completable deletePlannedMeal(PlannedMealEntity meal);

    Flowable<List<PlannedMealEntity>> getMealsByDay(String userId, String day);
}

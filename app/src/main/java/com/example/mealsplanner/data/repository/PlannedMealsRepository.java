package com.example.mealsplanner.data.repository;


import com.example.mealsplanner.data.domain.entity.PlannedMealEntity;
import com.example.mealsplanner.data.source.planner.PlannedMealLocalDataSource;
import com.example.mealsplanner.data.source.remote.planner.PlannedMealRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class PlannedMealsRepository {

    private final PlannedMealLocalDataSource localDataSource;
    private final PlannedMealRemoteDataSource remoteDataSource;

    public PlannedMealsRepository(PlannedMealLocalDataSource localDataSource,
                                  PlannedMealRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public Completable insertPlannedMeal(String userId, PlannedMealEntity meal) {

        return localDataSource.insertPlannedMeal(meal)
                .andThen(remoteDataSource.insertPlannedMeal(userId, meal));
    }

    public Completable deletePlannedMeal(String userId, PlannedMealEntity meal) {

        return localDataSource.deletePlannedMeal(meal)
                .andThen(
                        remoteDataSource.deletePlannedMeal(userId, meal)
                                .onErrorComplete()   // ignore remote failure
                );
    }

    public Flowable<List<PlannedMealEntity>> getMealsByDay(String userId, String day) {

        return localDataSource.getMealsByDay(userId, day);
    }
}

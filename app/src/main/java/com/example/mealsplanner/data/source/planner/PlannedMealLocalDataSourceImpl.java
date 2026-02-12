package com.example.mealsplanner.data.source.planner;

import com.example.mealsplanner.data.domain.entity.PlannedMealEntity;
import com.example.mealsplanner.data.source.local.dao.PlannedMealDao;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class PlannedMealLocalDataSourceImpl implements PlannedMealLocalDataSource {

    private final PlannedMealDao plannedMealDao;

    public PlannedMealLocalDataSourceImpl(PlannedMealDao plannedMealDao) {
        this.plannedMealDao = plannedMealDao;
    }

    @Override
    public Completable insertPlannedMeal(PlannedMealEntity meal) {
        return plannedMealDao.insertPlannedMeal(meal);
    }

    @Override
    public Completable deletePlannedMeal(PlannedMealEntity meal) {
        return plannedMealDao.deletePlannedMeal(meal);
    }

    @Override
    public Flowable<List<PlannedMealEntity>> getMealsByDay(String userId, String day) {
        return plannedMealDao.getMealsByDay(userId, day);
    }
}

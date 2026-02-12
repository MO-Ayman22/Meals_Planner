package com.example.mealsplanner.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealsplanner.data.domain.entity.PlannedMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PlannedMealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPlannedMeal(PlannedMealEntity meal);

    @Delete
    Completable deletePlannedMeal(PlannedMealEntity meal);

    @Query("SELECT * FROM planned_meals WHERE userId = :userId AND day = :day")
    Flowable<List<PlannedMealEntity>> getMealsByDay(String userId, String day);

}


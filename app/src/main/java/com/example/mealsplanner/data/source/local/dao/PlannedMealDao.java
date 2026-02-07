package com.example.mealsplanner.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealsplanner.data.model.entity.PlannedMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PlannedMealDao {

    @Query("SELECT * FROM planned_meals WHERE day = :day")
    Flowable<List<PlannedMealEntity>> getMealsByDay(String day);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(PlannedMealEntity meal);

    @Query("DELETE FROM planned_meals WHERE planId = :planId")
    Completable delete(int planId);
}

package com.example.mealsplanner.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealsplanner.data.domain.entity.MealEntity;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealDao {
    @Query("SELECT * FROM meals WHERE id = :id")
    Single<MealEntity> getMeal(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(MealEntity meal);

    @Query("DELETE FROM meals WHERE id = :mealId")
    Completable delete(String mealId);
}

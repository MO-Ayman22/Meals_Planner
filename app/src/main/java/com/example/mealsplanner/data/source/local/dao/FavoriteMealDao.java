package com.example.mealsplanner.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealsplanner.data.model.entity.FavoriteMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface FavoriteMealDao {

    @Query("SELECT * FROM favorite_meals")
    Flowable<List<FavoriteMealEntity>> getFavoriteMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(FavoriteMealEntity meal);

    @Query("DELETE FROM favorite_meals WHERE id = :mealId")
    Completable delete(String mealId);
}

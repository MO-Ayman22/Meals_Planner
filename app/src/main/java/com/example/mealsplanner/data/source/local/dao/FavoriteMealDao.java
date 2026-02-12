package com.example.mealsplanner.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealsplanner.data.model.entity.FavoriteMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavoriteMealDao {

    @Query("SELECT * FROM favorite_meals WHERE userId = :userId")
    Flowable<List<FavoriteMealEntity>> getFavorites(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(FavoriteMealEntity meal);

    @Query("DELETE FROM favorite_meals WHERE userId = :userId AND id = :mealId")
    Completable delete(String userId, String mealId);

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE userId = :userId AND id = :mealId)")
    Single<Boolean> isFavorite(String userId, String mealId);

    @Query("DELETE FROM favorite_meals WHERE userId = :userId")
    Completable clear(String userId);
}


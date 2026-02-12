package com.example.mealsplanner.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealsplanner.data.model.entity.CategoryEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;


@Dao
public interface CategoryDao {

    @Query("SELECT * FROM categories")
    Single<List<CategoryEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<CategoryEntity> categories);

    @Query("DELETE FROM categories")
    Completable clear();
}

package com.example.mealsplanner.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealsplanner.data.domain.entity.UserEntity;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE uid = :uid LIMIT 1")
    Single<UserEntity> getUser(String uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable saveUser(UserEntity user);
}

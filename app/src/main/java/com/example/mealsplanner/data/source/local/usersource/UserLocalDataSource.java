package com.example.mealsplanner.data.source.local.usersource;

import com.example.mealsplanner.data.model.entity.UserEntity;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface UserLocalDataSource {
    Single<UserEntity> getUser(String uid);

    Completable saveUser(UserEntity user);

}

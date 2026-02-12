package com.example.mealsplanner.data.source.remote.usersource;

import androidx.annotation.NonNull;

import com.example.mealsplanner.data.domain.model.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface UserRemoteDataSource {
    Completable createUser(@NonNull User user);

    Single<User> getUser(String uid);

    Single<Boolean> exists(@NonNull String uid);

    Single<Boolean> existsByEmail(@NonNull String email);
}

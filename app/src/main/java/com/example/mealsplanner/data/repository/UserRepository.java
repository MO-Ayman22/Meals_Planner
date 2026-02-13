package com.example.mealsplanner.data.repository;

import androidx.annotation.NonNull;

import com.example.mealsplanner.data.domain.model.User;
import com.example.mealsplanner.data.source.local.usersource.UserLocalDataSource;
import com.example.mealsplanner.data.source.remote.usersource.UserRemoteDataSource;
import com.example.mealsplanner.data.source.remote.usersource.UserRemoteDataSourceImpl;
import com.example.mealsplanner.util.mapper.UserMapper;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class UserRepository {

    private final UserRemoteDataSource remoteSource;
    private final UserLocalDataSource localSource;

    public UserRepository(UserRemoteDataSourceImpl remoteSource, UserLocalDataSource localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }


    public Completable saveLocalUser(User user) {
        return localSource.saveUser(UserMapper.toEntity(user));
    }

    public Completable create(@NonNull User user) {
        return remoteSource.createUser(user);
    }

    public Single<Boolean> exists(@NonNull String uid) {
        return remoteSource.exists(uid);
    }

    public Single<Boolean> existsByEmail(@NonNull String email) {
        return remoteSource.existsByEmail(email);
    }

    public Single<User> getUser(String uid) {
        return localSource.getUser(uid)
                .map(UserMapper::toModel)
                .onErrorResumeNext(error ->
                        remoteSource.getUser(uid)
                                .flatMap(user ->
                                        saveLocalUser(user)
                                                .andThen(Single.just(user))
                                )
                );
    }

}


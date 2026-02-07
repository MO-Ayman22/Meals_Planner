package com.example.mealsplanner.data.source.local.usersource;

import com.example.mealsplanner.data.source.local.dao.UserDao;
import com.example.mealsplanner.data.source.local.entity.UserEntity;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class UserLocalDataSourceImpl implements UserLocalDataSource {

    private final UserDao dao;

    public UserLocalDataSourceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public Single<UserEntity> getUser(String uid) {
        return dao.getUser(uid);
    }

    @Override
    public Completable saveUser(UserEntity user) {
        return dao.saveUser(user);
    }
}


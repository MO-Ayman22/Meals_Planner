package com.example.mealsplanner.data.source.local.localsources;

import com.example.mealsplanner.data.source.local.dao.UserDao;
import com.example.mealsplanner.data.source.local.entity.UserEntity;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class UserLocalDataSource {

    private final UserDao dao;

    public UserLocalDataSource(UserDao dao) {
        this.dao = dao;
    }

    public Single<UserEntity> getUser(String uid) {
        return dao.getUser(uid);
    }

    public Completable saveUser(UserEntity user) {
        return dao.saveUser(user);
    }
}


package com.example.mealsplanner.core;

import android.annotation.SuppressLint;
import android.app.Application;

import com.example.mealsplanner.data.repository.UserRepository;
import com.example.mealsplanner.data.source.local.db.AppDatabase;
import com.example.mealsplanner.data.source.local.localsources.UserLocalDataSource;
import com.example.mealsplanner.data.source.remote.firestore.UserRemoteDataSource;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BaseApplication extends Application {
    private static volatile BaseApplication instance;
    private SessionManager sessionManager;
    private ConnectivityObserver connectivityObserver;

    public static BaseApplication getInstance() {
        if (instance == null) {
            synchronized (BaseApplication.class) {
                if (instance == null) {
                    instance = new BaseApplication();
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sessionManager = SessionManager.getInstance(this);

        connectivityObserver = ConnectivityObserver.getInstance(this);

        if (sessionManager.isLoggedIn()) {
            loadCurrentUser(sessionManager.getUserId());
        }
    }

    public SessionManager session() {
        return sessionManager;
    }

    public ConnectivityObserver connectivity() {
        return connectivityObserver;
    }

    @SuppressLint("CheckResult")
    private void loadCurrentUser(String userId) {
        UserRepository userRepo = new UserRepository(
                new UserRemoteDataSource(),
                new UserLocalDataSource(AppDatabase.getInstance(this).getUserDAO())
        );

        userRepo.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(CurrentUserHolder::setUser, throwable -> {
                });
    }

}

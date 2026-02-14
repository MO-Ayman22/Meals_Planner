package com.example.mealsplanner.core;

import android.annotation.SuppressLint;
import android.app.Application;

import com.example.mealsplanner.data.repository.UserRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BaseApplication extends Application {

    private static volatile BaseApplication instance;
    private SessionManager sessionManager;
    private AppPreferencesManager preferencesManager;
    private ConnectivityObserver connectivityObserver;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        sessionManager = SessionManager.getInstance(this);
        preferencesManager = AppPreferencesManager.getInstance(this);
        connectivityObserver = ConnectivityObserver.getInstance(this);

        if (sessionManager.isLoggedIn()) {
            loadCurrentUser(sessionManager.getUserId());
        }
        preferencesManager.clearRandomMeal();
    }

    public SessionManager session() {
        return sessionManager;
    }

    public AppPreferencesManager preferences() {
        return preferencesManager;
    }

    public ConnectivityObserver connectivity() {
        return connectivityObserver;
    }

    @SuppressLint("CheckResult")
    private void loadCurrentUser(String userId) {
        UserRepository userRepository = RepositoryProvider.provideUserRepository(this);

        userRepository.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(CurrentUserHolder::setUser, throwable -> {

                });
    }
}


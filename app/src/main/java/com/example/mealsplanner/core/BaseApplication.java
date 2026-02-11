package com.example.mealsplanner.core;

import android.annotation.SuppressLint;
import android.app.Application;
import android.widget.Toast;

import com.example.mealsplanner.data.repository.UserRepository;
import com.example.mealsplanner.data.source.local.db.AppDatabase;
import com.example.mealsplanner.data.source.local.usersource.UserLocalDataSourceImpl;
import com.example.mealsplanner.data.source.remote.usersource.UserRemoteDataSourceImpl;

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

    @SuppressLint("CheckResult")
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sessionManager = SessionManager.getInstance(this);

        connectivityObserver = ConnectivityObserver.getInstance(this);

        if (sessionManager.isLoggedIn()) {
            loadCurrentUser(sessionManager.getUserId());
        }
        connectivityObserver.observe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected -> {
                    if (isConnected) {
                        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show();
                    }
                });
        sessionManager.saveRandomMeal(null);

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
                new UserRemoteDataSourceImpl(),
                new UserLocalDataSourceImpl(AppDatabase.getInstance(this).getUserDAO())
        );

        userRepo.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(CurrentUserHolder::setUser, throwable -> {
                });
    }

}

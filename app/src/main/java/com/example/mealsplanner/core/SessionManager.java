package com.example.mealsplanner.core;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.example.mealsplanner.data.repository.UserRepository;
import com.example.mealsplanner.data.source.local.db.AppDatabase;
import com.example.mealsplanner.data.source.local.usersource.UserLocalDataSourceImpl;
import com.example.mealsplanner.data.source.remote.usersource.UserRemoteDataSourceImpl;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SessionManager {
    private static final String PREF_NAME = "user_session";


    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_RANDOM_MEAL = "random_meal";
    private static final String KEY_USER_ID = "user_id";
    private static volatile SessionManager instance;
    private final SharedPreferences prefs;

    private SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SessionManager getInstance(Context context) {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void saveLoginSession(String userId) {
        prefs.edit()
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .putString(KEY_USER_ID, userId)
                .apply();
        loadCurrentUser(userId);
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    @Nullable
    public String getUserId() {
        return prefs.getString(KEY_USER_ID, null);
    }

    public void logout() {
        prefs.edit()
                .putBoolean(KEY_IS_LOGGED_IN, false).apply();
    }

    public void saveRandomMeal(String randomMealId) {
        prefs.edit()
                .putString(KEY_RANDOM_MEAL, randomMealId)
                .apply();
    }

    public String getRandomMeal() {
        return prefs.getString(KEY_RANDOM_MEAL, null);
    }

    private void loadCurrentUser(String userId) {
        UserRepository userRepo = new UserRepository(
                new UserRemoteDataSourceImpl(),
                new UserLocalDataSourceImpl(AppDatabase.getInstance(BaseApplication.getInstance()).getUserDAO())
        );

        Disposable disposable = userRepo.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(CurrentUserHolder::setUser, throwable -> {
                });
    }

}

package com.example.mealsplanner.core;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

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

}

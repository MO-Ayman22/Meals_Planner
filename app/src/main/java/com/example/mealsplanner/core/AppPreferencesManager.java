package com.example.mealsplanner.core;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferencesManager {

    private static final String PREF_NAME = "app_preferences";
    private static final String KEY_RANDOM_MEAL = "random_meal";

    private static volatile AppPreferencesManager instance;

    private final SharedPreferences prefs;

    private AppPreferencesManager(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static AppPreferencesManager getInstance(Context context) {
        if (instance == null) {
            synchronized (AppPreferencesManager.class) {
                if (instance == null) {
                    instance = new AppPreferencesManager(context);
                }
            }
        }
        return instance;
    }

    public void saveRandomMeal(String mealId) {
        prefs.edit().putString(KEY_RANDOM_MEAL, mealId).apply();
    }

    public String getRandomMeal() {
        return prefs.getString(KEY_RANDOM_MEAL, null);
    }

    public void clearRandomMeal() {
        prefs.edit().putString(KEY_RANDOM_MEAL, null).apply();
    }
}

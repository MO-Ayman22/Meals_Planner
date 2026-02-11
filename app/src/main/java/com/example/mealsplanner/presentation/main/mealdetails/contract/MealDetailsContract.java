package com.example.mealsplanner.presentation.main.mealdetails.contract;

import com.example.mealsplanner.data.model.domain.Meal;

public interface MealDetailsContract {
    interface View {
        void showMeal(Meal meal);

        void showFavorite(Boolean aBoolean);
    }

    interface Presenter {
        void getMeal(String mealId);

        void isFavorite(String mealId);

        void toggleFavorite(Meal meal, boolean isFavorite);
    }
}

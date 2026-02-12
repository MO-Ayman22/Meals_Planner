package com.example.mealsplanner.presentation.main.mealdetails.contract;

import com.example.mealsplanner.data.domain.model.Meal;

public interface MealDetailsContract {

    interface View {
        void showMeal(Meal meal);

        void showFavorite(Boolean isFavorite);

        void showMessage(String message);
    }

    interface Presenter {
        void getMeal(String mealId);

        void isFavorite(String mealId);

        void toggleFavorite(Meal meal, boolean isFavorite);

        void addMealToPlanner(Meal meal, String day);

        void clear();
    }
}

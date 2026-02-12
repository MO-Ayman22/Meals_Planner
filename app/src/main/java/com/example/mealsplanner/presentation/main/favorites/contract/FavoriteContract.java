package com.example.mealsplanner.presentation.main.favorites.contract;

import com.example.mealsplanner.data.domain.model.Meal;

import java.util.List;

public class FavoriteContract {
    public interface View {
        void showMeals(List<Meal> meals);

        void showError(String message);

        void showSuccessMessage(String message);
    }

    public interface Presenter {
        void getFavoriteMeals();

        void removeFavoriteMeal(String mealId);

        void clear();

    }

}

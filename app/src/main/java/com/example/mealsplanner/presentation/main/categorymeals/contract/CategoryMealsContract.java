package com.example.mealsplanner.presentation.main.categorymeals.contract;

import com.example.mealsplanner.data.model.domain.MealPreview;

import java.util.List;

public interface CategoryMealsContract {
    interface View {
        void showMeals(List<MealPreview> meals);
    }

    interface Presenter {
        void getMealsByCategory(String category);
    }
}

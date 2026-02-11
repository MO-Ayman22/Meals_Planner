package com.example.mealsplanner.presentation.main.areameals.contract;

import com.example.mealsplanner.data.model.domain.MealPreview;

import java.util.List;

public class AreaMealsContract {
    public interface View {
        void showMeals(List<MealPreview> meals);
    }

    public interface Presenter {
        void getMealsByArea(String area);
    }
}

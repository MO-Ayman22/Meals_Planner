package com.example.mealsplanner.presentation.main.planner.contract;

import com.example.mealsplanner.data.domain.entity.PlannedMealEntity;
import com.example.mealsplanner.data.domain.model.DayModel;

import java.util.List;

public interface PlannerContract {

    interface View {

        void showMeals(List<PlannedMealEntity> meals);

        void showEmptyState();

        void hideEmptyState();

        void updateWeekRange(String weekRange);

        void showMessage(String message);

        void updateWeek(List<DayModel> week);
    }

    interface Presenter {

        void loadMealsForDay(String date);

        void nextWeek();

        void previousWeek();

        void deleteMeal(PlannedMealEntity meal);

        void clear();
    }
}

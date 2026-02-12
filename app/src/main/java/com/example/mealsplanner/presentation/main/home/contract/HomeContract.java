package com.example.mealsplanner.presentation.main.home.contract;

import com.example.mealsplanner.data.domain.model.Area;
import com.example.mealsplanner.data.domain.model.Category;
import com.example.mealsplanner.data.domain.model.Meal;

import java.util.List;

public class HomeContract {
    public interface View {
        void showCategories(List<Category> categories);

        void showAreas(List<Area> areas);

        void showRandomMeal(Meal meal);

        void LostConnection();

        void Connected();
    }

    public interface Presenter {
        void getCategories();

        void getAreas();

        void getRandomMeal();

        void internetObserve();

        void clear();
    }
}

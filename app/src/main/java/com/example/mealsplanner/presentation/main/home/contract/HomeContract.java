package com.example.mealsplanner.presentation.main.home.contract;

import com.example.mealsplanner.data.model.domain.Area;
import com.example.mealsplanner.data.model.domain.Category;
import com.example.mealsplanner.data.model.domain.Meal;

import java.util.List;

public class HomeContract {
    public interface View {
        void showCategories(List<Category> categories);

        void showAreas(List<Area> areas);

        void showRandomMeal(Meal meal);
    }

    public interface Presenter {
        void getCategories();

        void getAreas();

        void getRandomMeal();
    }
}

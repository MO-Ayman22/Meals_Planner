package com.example.mealsplanner.presentation.main.search.contract;

import com.example.mealsplanner.data.domain.model.Meal;

import java.util.List;

public interface SearchContract {

    interface View {
        void showMeals(List<Meal> meals);

        void showLoading();

        void hideLoading();

        void showError(String message);

        void updateResultsCount(int count);

        void showCountries(List<String> countries);

        void showCategories(List<String> categories);

        void LostConnection();

        void Connected();
    }

    interface Presenter {
        void search(String query);

        void applyFilters(String country, String category);

        void loadFilters();

        void internetObserve();

        void clear();
    }
}

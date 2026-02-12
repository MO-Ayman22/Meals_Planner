package com.example.mealsplanner.presentation.main.categories.contract;

import com.example.mealsplanner.data.domain.model.Category;

import java.util.List;

public interface CategoriesContract {
    interface View {
        void showCategories(List<Category> categories);
    }

    interface Presenter {
        void getCategories();
    }
}

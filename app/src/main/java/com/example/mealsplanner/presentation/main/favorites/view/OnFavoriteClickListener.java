package com.example.mealsplanner.presentation.main.favorites.view;

import com.example.mealsplanner.data.domain.model.Meal;

public interface OnFavoriteClickListener {
    void onFavouriteClick(Meal meal);

    void onDeleteClick(Meal meal);
}

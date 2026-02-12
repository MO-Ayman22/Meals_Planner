package com.example.mealsplanner.data.domain.wrapper;

import com.example.mealsplanner.data.domain.dto.MealPreviewDto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealPreviewListResponse {
    @SerializedName("meals")
    private List<MealPreviewDto> meals;

    public MealPreviewListResponse(List<MealPreviewDto> meals) {
        this.meals = meals;
    }

    public List<MealPreviewDto> getMeals() {
        return meals;

    }

    public void setMeals(List<MealPreviewDto> meals) {
        this.meals = meals;

    }
}
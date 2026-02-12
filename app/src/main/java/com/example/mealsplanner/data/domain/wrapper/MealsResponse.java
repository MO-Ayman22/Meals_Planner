package com.example.mealsplanner.data.domain.wrapper;

import com.example.mealsplanner.data.domain.dto.MealDto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealsResponse {
    @SerializedName("meals")
    private List<MealDto> meals;

    public List<MealDto> getMeals() {
        return meals;
    }
}


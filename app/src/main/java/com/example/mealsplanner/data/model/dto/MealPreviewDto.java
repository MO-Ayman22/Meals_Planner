package com.example.mealsplanner.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class MealPreviewDto {

    @SerializedName("idMeal")
    private String id;

    @SerializedName("strMeal")
    private String name;

    @SerializedName("strMealThumb")
    private String image;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

}
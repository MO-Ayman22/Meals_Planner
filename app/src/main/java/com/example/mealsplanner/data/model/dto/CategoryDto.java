package com.example.mealsplanner.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class CategoryDto {

    @SerializedName("idCategory")
    private String id;

    @SerializedName("strCategory")
    private String name;

    @SerializedName("strCategoryThumb")
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


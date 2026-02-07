package com.example.mealsplanner.data.model.wrapper;

import com.example.mealsplanner.data.model.dto.CategoryDto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesResponse {
    @SerializedName("categories")
    private List<CategoryDto> categories;

    public CategoriesResponse(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }


}


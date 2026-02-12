package com.example.mealsplanner.data.domain.dto;

import com.google.gson.annotations.SerializedName;

public class AreaDto {

    @SerializedName("strArea")
    private String name;

    public String getName() {
        return name;
    }
}


package com.example.mealsplanner.data.model.wrapper;

import com.example.mealsplanner.data.model.dto.AreaDto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaListResponse {
    @SerializedName("meals")
    private List<AreaDto> areas;

    public List<AreaDto> getAreas() {
        return areas;
    }
}


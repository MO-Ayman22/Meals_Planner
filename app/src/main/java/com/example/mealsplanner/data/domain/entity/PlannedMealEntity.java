package com.example.mealsplanner.data.domain.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mealsplanner.util.StringListConverter;

import java.util.List;

@Entity(
        tableName = "planned_meals",
        indices = {
                @Index(value = {"userId", "day", "mealId"}, unique = true)
        }
)
public class PlannedMealEntity {

    private final String userId;
    @PrimaryKey(autoGenerate = true)
    private int planId;
    private String day;
    private final String mealId;
    private final String name;
    private final String category;
    private final String instructions;
    private final String image;
    private final String area;
    @TypeConverters(StringListConverter.class)
    private final List<String> ingredients;
    @TypeConverters(StringListConverter.class)
    private final List<String> measures;
    private final String youtube;

    public PlannedMealEntity(String userId, String day, String mealId, String name, String category, String area, String instructions, String image, List<String> ingredients, List<String> measures, String youtube) {
        this.userId = userId;
        this.day = day;
        this.mealId = mealId;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.image = image;
        this.ingredients = ingredients;
        this.measures = measures;
        this.youtube = youtube;
    }

    public String getUserId() {
        return userId;
    }

    public int getPlanId() {
        return planId;
    }

    public String getDay() {
        return day;
    }

    public String getMealId() {
        return mealId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getImage() {
        return image;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getMeasures() {
        return measures;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

}



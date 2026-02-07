package com.example.mealsplanner.data.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mealsplanner.util.StringListConverter;

import java.util.List;

@Entity(tableName = "planned_meals")
public class PlannedMealEntity {

    String name;
    String category;
    String area;
    String instructions;
    String image;
    @TypeConverters(StringListConverter.class)
    List<String> ingredients;
    @TypeConverters(StringListConverter.class)
    List<String> measures;
    String youtube;
    @PrimaryKey(autoGenerate = true)
    private int planId;
    private String day;
    private String mealId;


    public PlannedMealEntity(String mealId, String name, String day,
                             String category, String area, String image,
                             List<String> ingredients, List<String> measures, String youtube, String instructions) {
        this.mealId = mealId;
        this.name = name;
        this.day = day;
        this.category = category;
        this.area = area;
        this.image = image;
        this.ingredients = ingredients;
        this.measures = measures;
        this.youtube = youtube;
        this.instructions = instructions;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getMeasures() {
        return measures;
    }

    public void setMeasures(List<String> measures) {
        this.measures = measures;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}


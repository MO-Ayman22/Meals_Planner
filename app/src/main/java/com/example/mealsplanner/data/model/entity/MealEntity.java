package com.example.mealsplanner.data.model.entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mealsplanner.util.StringListConverter;

import java.util.List;

@Entity(tableName = "meals")
public class MealEntity {
    @PrimaryKey
    @NonNull
    String id;
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

    public MealEntity(String id, String name, String category, String area, String instructions, String image, List<String> ingredients, List<String> measures, String youtube) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.image = image;
        this.ingredients = ingredients;
        this.measures = measures;
        this.youtube = youtube;
    }

    public String getId() {
        return id;
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

    public String getInstructions() {
        return instructions;
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
}


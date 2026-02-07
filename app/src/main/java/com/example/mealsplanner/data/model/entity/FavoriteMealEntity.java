package com.example.mealsplanner.data.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mealsplanner.util.StringListConverter;

import java.util.List;

@Entity(tableName = "favorite_meals")
public class FavoriteMealEntity {

    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String category;
    private String area;
    private String instructions;
    private String image;

    @TypeConverters(StringListConverter.class)
    private List<String> ingredients;

    @TypeConverters(StringListConverter.class)
    private List<String> measures;
    private String youtube;

    public FavoriteMealEntity(@NonNull String id, String name, String category, String area,
                              String instructions, String image, List<String> ingredients,
                              List<String> measures, String youtube) {
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

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
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

}

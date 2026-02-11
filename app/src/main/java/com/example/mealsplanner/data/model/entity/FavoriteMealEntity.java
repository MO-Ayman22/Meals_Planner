package com.example.mealsplanner.data.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.mealsplanner.util.StringListConverter;

import java.util.List;

@Entity(
        tableName = "favorite_meals",
        primaryKeys = {"userId", "id"}
)
public class FavoriteMealEntity {

    @NonNull
    private final String userId;
    @NonNull
    private final String id;
    private final String name;
    String category;
    String area;
    String instructions;
    String image;
    @TypeConverters(StringListConverter.class)
    List<String> ingredients;
    @TypeConverters(StringListConverter.class)
    List<String> measures;
    String youtube;

    public FavoriteMealEntity(@NonNull String userId, @NonNull String id, String name, String category, String area, String instructions, String image, List<String> ingredients, List<String> measures, String youtube) {
        this.userId = userId;
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.measures = measures;
        this.youtube = youtube;

    }

    @NonNull
    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getImage() {
        return image;
    }

    @NonNull
    public String getUserId() {
        return userId;
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

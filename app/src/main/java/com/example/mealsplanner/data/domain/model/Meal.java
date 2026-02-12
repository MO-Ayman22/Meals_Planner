package com.example.mealsplanner.data.domain.model;

import java.util.List;

public class Meal {
    String id;
    String name;
    String category;
    String area;
    String instructions;
    String image;
    List<String> ingredients;
    List<String> measures;
    String youtube;

    public Meal(String id, String name, String category, String area, String instructions, String image, List<String> ingredients, List<String> measures, String youtube) {
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


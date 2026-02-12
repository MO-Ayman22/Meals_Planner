package com.example.mealsplanner.data.domain.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MealDto {

    @SerializedName("idMeal")
    private String id;

    @SerializedName("strMeal")
    private String name;

    @SerializedName("strCategory")
    private String category;

    @SerializedName("strArea")
    private String area;

    @SerializedName("strInstructions")
    private String instructions;

    @SerializedName("strMealThumb")
    private String image;

    @SerializedName("strYoutube")
    private String youtube;

    // ingredients 1..20
    @SerializedName("strIngredient1")
    private String ingredient1;
    @SerializedName("strIngredient2")
    private String ingredient2;
    @SerializedName("strIngredient3")
    private String ingredient3;
    @SerializedName("strIngredient4")
    private String ingredient4;
    @SerializedName("strIngredient5")
    private String ingredient5;
    @SerializedName("strIngredient6")
    private String ingredient6;
    @SerializedName("strIngredient7")
    private String ingredient7;
    @SerializedName("strIngredient8")
    private String ingredient8;
    @SerializedName("strIngredient9")
    private String ingredient9;
    @SerializedName("strIngredient10")
    private String ingredient10;
    @SerializedName("strIngredient11")
    private String ingredient11;
    @SerializedName("strIngredient12")
    private String ingredient12;
    @SerializedName("strIngredient13")
    private String ingredient13;
    @SerializedName("strIngredient14")
    private String ingredient14;
    @SerializedName("strIngredient15")
    private String ingredient15;
    @SerializedName("strIngredient16")
    private String ingredient16;
    @SerializedName("strIngredient17")
    private String ingredient17;
    @SerializedName("strIngredient18")
    private String ingredient18;
    @SerializedName("strIngredient19")
    private String ingredient19;
    @SerializedName("strIngredient20")
    private String ingredient20;

    // measures 1..20
    @SerializedName("strMeasure1")
    private String measure1;
    @SerializedName("strMeasure2")
    private String measure2;
    @SerializedName("strMeasure3")
    private String measure3;
    @SerializedName("strMeasure4")
    private String measure4;
    @SerializedName("strMeasure5")
    private String measure5;
    @SerializedName("strMeasure6")
    private String measure6;
    @SerializedName("strMeasure7")
    private String measure7;
    @SerializedName("strMeasure8")
    private String measure8;
    @SerializedName("strMeasure9")
    private String measure9;
    @SerializedName("strMeasure10")
    private String measure10;
    @SerializedName("strMeasure11")
    private String measure11;
    @SerializedName("strMeasure12")
    private String measure12;
    @SerializedName("strMeasure13")
    private String measure13;
    @SerializedName("strMeasure14")
    private String measure14;
    @SerializedName("strMeasure15")
    private String measure15;
    @SerializedName("strMeasure16")
    private String measure16;
    @SerializedName("strMeasure17")
    private String measure17;
    @SerializedName("strMeasure18")
    private String measure18;
    @SerializedName("strMeasure19")
    private String measure19;
    @SerializedName("strMeasure20")
    private String measure20;

    // ...
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

    public String getYoutube() {
        return youtube;
    }

    public List<String> getIngredients() {
        List<String> ingredients = new ArrayList<>();
        if (ingredient1 != null && !ingredient1.isEmpty()) ingredients.add(ingredient1);
        if (ingredient2 != null && !ingredient2.isEmpty()) ingredients.add(ingredient2);
        if (ingredient3 != null && !ingredient3.isEmpty()) ingredients.add(ingredient3);
        if (ingredient4 != null && !ingredient4.isEmpty()) ingredients.add(ingredient4);
        if (ingredient5 != null && !ingredient5.isEmpty()) ingredients.add(ingredient5);
        if (ingredient6 != null && !ingredient6.isEmpty()) ingredients.add(ingredient6);
        if (ingredient7 != null && !ingredient7.isEmpty()) ingredients.add(ingredient7);
        if (ingredient8 != null && !ingredient8.isEmpty()) ingredients.add(ingredient8);
        if (ingredient9 != null && !ingredient9.isEmpty()) ingredients.add(ingredient9);
        if (ingredient10 != null && !ingredient10.isEmpty()) ingredients.add(ingredient10);
        if (ingredient11 != null && !ingredient11.isEmpty()) ingredients.add(ingredient11);
        if (ingredient12 != null && !ingredient12.isEmpty()) ingredients.add(ingredient12);
        if (ingredient13 != null && !ingredient13.isEmpty()) ingredients.add(ingredient13);
        if (ingredient14 != null && !ingredient14.isEmpty()) ingredients.add(ingredient14);
        if (ingredient15 != null && !ingredient15.isEmpty()) ingredients.add(ingredient15);
        if (ingredient16 != null && !ingredient16.isEmpty()) ingredients.add(ingredient16);
        if (ingredient17 != null && !ingredient17.isEmpty()) ingredients.add(ingredient17);
        if (ingredient18 != null && !ingredient18.isEmpty()) ingredients.add(ingredient18);
        if (ingredient19 != null && !ingredient19.isEmpty()) ingredients.add(ingredient19);
        if (ingredient20 != null && !ingredient20.isEmpty()) ingredients.add(ingredient20);
        return ingredients;
    }

    public List<String> getMeasures() {
        List<String> measures = new ArrayList<>();
        if (measure1 != null && !measure1.isEmpty()) measures.add(measure1);
        if (measure2 != null && !measure2.isEmpty()) measures.add(measure2);
        if (measure3 != null && !measure3.isEmpty()) measures.add(measure3);
        if (measure4 != null && !measure4.isEmpty()) measures.add(measure4);
        if (measure5 != null && !measure5.isEmpty()) measures.add(measure5);
        if (measure6 != null && !measure6.isEmpty()) measures.add(measure6);
        if (measure7 != null && !measure7.isEmpty()) measures.add(measure7);
        if (measure8 != null && !measure8.isEmpty()) measures.add(measure8);
        if (measure9 != null && !measure9.isEmpty()) measures.add(measure9);
        if (measure10 != null && !measure10.isEmpty()) measures.add(measure10);
        if (measure11 != null && !measure11.isEmpty()) measures.add(measure11);
        if (measure12 != null && !measure12.isEmpty()) measures.add(measure12);
        if (measure13 != null && !measure13.isEmpty()) measures.add(measure13);
        if (measure14 != null && !measure14.isEmpty()) measures.add(measure14);
        if (measure15 != null && !measure15.isEmpty()) measures.add(measure15);
        if (measure16 != null && !measure16.isEmpty()) measures.add(measure16);
        if (measure17 != null && !measure17.isEmpty()) measures.add(measure17);
        if (measure18 != null && !measure18.isEmpty()) measures.add(measure18);
        if (measure19 != null && !measure19.isEmpty()) measures.add(measure19);
        if (measure20 != null && !measure20.isEmpty()) measures.add(measure20);
        return measures;
    }
}
package com.example.mealsplanner.data.model.domain;

public class Category {
    String id;
    String name;
    String image;

    public Category(String name) {
        this.name = name;
    }

    public Category(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}


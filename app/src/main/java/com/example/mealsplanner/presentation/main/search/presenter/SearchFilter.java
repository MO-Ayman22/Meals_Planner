package com.example.mealsplanner.presentation.main.search.presenter;

public class SearchFilter {
    private String query;
    private String country;
    private String category;

    public SearchFilter() {
        this.query = "";
        this.country = "All";
        this.category = "All";
    }

    // getters & setters
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

package com.example.mealsplanner.data.domain.model;

public class DayModel {
    private String dayName;
    private String dayNumber;
    private String fullDate;
    private boolean isSelected = false;

    public DayModel(String dayName, String dayNumber, String fullDate, boolean isSelected) {
        this.dayName = dayName;
        this.dayNumber = dayNumber;
        this.fullDate = fullDate;
        this.isSelected = isSelected;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getFullDate() {
        return fullDate;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}

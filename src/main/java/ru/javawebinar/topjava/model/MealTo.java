package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealTo {
    private int mealToId;

    public int getMealToId() {
        return mealToId;
    }

    public void setMealToId(int mealToId) {
        this.mealToId = mealToId;
    }

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    public MealTo(int id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.mealToId = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}

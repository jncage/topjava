package ru.javawebinar.topjava.impl;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ImplMealDaoInMemory implements MealDao {
    private AtomicInteger id = new AtomicInteger(1);
    private static Map<Integer, Meal> storage = new ConcurrentHashMap<>();


    public ImplMealDaoInMemory() { MealsUtil.MEALS.stream().forEach(this::addMeal);

    }

    @Override
    public void addMeal(Meal meal) {
        int mealId = id.getAndIncrement();
        meal.setMealId(mealId);
        storage.put(mealId, meal);

    }

    @Override
    public void deleteMeal(int mealId) {
        storage.remove(mealId);
    }

    @Override
    public void updateMeal(Meal meal) {
        storage.replace(meal.getMealId(), meal);
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Meal getMealById(int mealId) {
        return storage.get(mealId);
    }
}

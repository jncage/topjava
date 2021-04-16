package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final int ADMIN = 1;
    private static final int USER = 2;
    private static final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        AtomicInteger switcher = new AtomicInteger();
        MealsUtil.meals.forEach(m -> {
            if (switcher.getAndIncrement() % 2 == 0) {
                save(ADMIN, m);
            } else {
                save(USER, m);
            }
        });
    }

    @Override
    public Meal save(int userId, Meal meal) {

        Map<Integer, Meal> meals = repository.get(userId);
        if (meals == null) {
            meals = new ConcurrentHashMap<>();
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (checkUserMeals(userId, meal.getId())) {
            return null;
        }
        meals.put(meal.getId(), meal);
        repository.put(userId, meals);
        // handle case: update, but not present in storage
        return meal;
    }

    private static boolean checkUserMeals(int userId, int id) {
        Map<Integer, Meal> meals = repository.get(userId);
        if (meals == null) return true;
        return meals.keySet().stream().noneMatch(mealId -> mealId == id);
    }

    @Override
    public boolean delete(int userId, int mealId) {
        if (checkUserMeals(userId, mealId)) {
            return false;
        }
        Map<Integer, Meal> meals = repository.get(userId);
        if (meals == null) return false;
        return meals.remove(mealId) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        if (checkUserMeals(userId, mealId)) {
            return null;
        }
        Map<Integer, Meal> meals = repository.get(userId);
        if (meals == null) return null;
        return meals.get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        if (meals == null) return Collections.emptyList();
        return meals.values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());

    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        List<Meal> meals = getAll(userId);
        return meals.stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate.plusDays(1)))
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .collect(Collectors.toList());
    }


}


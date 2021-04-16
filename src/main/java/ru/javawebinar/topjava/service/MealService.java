package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.web.SecurityUtil.*;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(int userId, Meal meal) {
        return checkNotFound(repository.save(userId, meal), meal.getId() + " for this userId: " + userId);
    }

    public void delete(int userId, int id) {
        checkNotFound(repository.delete(userId, id), id + " for this userId: " + userId);
    }

    public Meal get(int userId, int id) {
        return checkNotFound(repository.get(userId, id), id + " for this userId: " + userId);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(int userId, Meal meal) {
        checkNotFound(repository.save(userId, meal), meal.getId() + " for this userId: " + userId);
    }

    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return repository.getFiltered(userId, startDate, endDate, startTime, endTime);
    }
}
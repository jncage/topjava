package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.impl.ImplMealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final Logger log = getLogger(MealServlet.class);
    private ImplMealDaoInMemory dao = new ImplMealDaoInMemory();
    private static String LIST_MEAL = "/meals.jsp";
    private static String INSERT_OR_EDIT = "/meal.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        String forward = "";
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            dao.deleteMeal(mealId);
            forward = LIST_MEAL;
            req.setAttribute("meals", filteredByStreams(dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = dao.getMealById(mealId);
            req.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMeal")){
            forward = LIST_MEAL;
            req.setAttribute("meals", filteredByStreams(dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        }
        else {
            forward = INSERT_OR_EDIT;
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime ldt = LocalDateTime.parse(req.getParameter("datetime"), fmt);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(ldt, description, calories);
        String mealId = req.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()){
            dao.addMeal(meal);
        }else{
            meal.setMealId(Integer.parseInt(mealId));
            dao.updateMeal(meal);
        }
        req.setAttribute("meals", filteredByStreams(dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        req.getRequestDispatcher(LIST_MEAL).forward(req, resp);



    }
}

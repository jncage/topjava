package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            User user1 = new User(null, "userName", "email@mail.ru", "password", Role.ADMIN);
//            User user2 = new User(null, "userNamc", "email1@mail.ru", "password", Role.USER);
//            adminUserController.create( user1);
//            adminUserController.create(user2);
//            adminUserController.getAll().forEach(System.out::println);
//            System.out.println(adminUserController.getByMail("email1@mail.ru"));
//            User user3 = new User(null, "userNamzed", "email1@mail.ru", "password", Role.USER);
//            adminUserController.update(user3, 2);
////            adminUserController.delete(2);
////            adminUserController.getAll().forEach(System.out::println);
////            System.out.println(adminUserController.get(1));
////            adminUserController.delete(1);
//            adminUserController.getAll().forEach(System.out::println);
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            Meal meal1 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 30), "Второй завтрак", 50);
            Meal meal2 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 30), "Второй завтрак", 50);
            mealRestController.create(meal1);
            mealRestController.create(meal2);
            mealRestController.getAll().forEach(System.out::println);
            System.out.println(mealRestController.get(3));
            mealRestController.delete(8);
            mealRestController.delete(9);
//            mealRestController.update(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 30), "Второй завтрак", 50), 1);
            mealRestController.getAll().forEach(System.out::println);




        }
    }
}

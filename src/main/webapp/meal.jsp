<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new meal</title>
</head>
<body>
<form method="POST" action='meals' name="frmAddMeal">
    <input type="hidden" name="mealId" value="${meal.mealId}">
    Date : <input type="datetime-local" name="datetime" value="${meal.dateTime}"> <br />
    Description : <input
        type="text" name="description"
        value="${meal.description}" /> <br />
    Calories : <input
        type="number" name="calories"
        value="${meal.calories}" /> <br />
        <input type="submit" value="Save" />
</form>
</body>
</html>

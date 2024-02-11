package com.omarInc.mymeal.recommendedMeals.view;

import com.omarInc.mymeal.model.Meal;

import java.util.List;

public interface RecommendedMealsView {
    void showRecommendedMeals(List<Meal> meals);
    void showError(String message);
}
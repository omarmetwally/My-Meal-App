package com.omarInc.mymeal.detailsSearch.view;

import com.omarInc.mymeal.model.Meal;

import java.util.List;

public interface MealsView {
    void showMeals(List<Meal> meals);
    void showError(String message);
}

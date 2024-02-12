package com.omarInc.mymeal.mealdetails.view;

import com.omarInc.mymeal.model.MealDetail;

public interface MealDetailView {
    void showMealDetails(MealDetail mealDetail);
    void showError(String errorMessage);
    // Add other methods for updating the UI as needed
}

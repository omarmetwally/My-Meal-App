package com.omarInc.mymeal.mealdetails.view;

import androidx.lifecycle.LifecycleOwner;

import com.omarInc.mymeal.model.MealDetail;

public interface MealDetailView {
    void showMealDetails(MealDetail mealDetail);
    void showError(String errorMessage);

    void setFavoriteStatus(boolean isFavorite);
    LifecycleOwner getLifecycleOwner();
}

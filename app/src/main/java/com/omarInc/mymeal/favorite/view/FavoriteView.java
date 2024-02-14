package com.omarInc.mymeal.favorite.view;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.model.MealDetail;

import java.util.List;

public interface FavoriteView {
    void displayMeals(List<Meal> meals);
    void displayError(String message);
    LifecycleOwner getLifecycleOwner();
}

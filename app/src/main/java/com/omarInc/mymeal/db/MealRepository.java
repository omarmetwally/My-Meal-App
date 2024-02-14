package com.omarInc.mymeal.db;

import androidx.lifecycle.LiveData;

import com.omarInc.mymeal.model.MealDetail;

import java.util.List;

public interface MealRepository {
    void insert(MealDetail mealDetail);

    LiveData<MealDetail> getMealById(String idMeal);

    void delete(MealDetail mealDetail);

    void deleteById(String idMeal);
    void toggleFavorite(MealDetail mealDetail);
    LiveData<Boolean> isFavorite(String mealId);
    LiveData<List<MealDetail>> getAllMeals();
    void clearAll();

    void insertAll(List<MealDetail> meals);

}

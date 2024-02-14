package com.omarInc.mymeal.mealdetails.presenter;

import com.omarInc.mymeal.model.MealDetail;

public interface MealDetailsPresenter {
    void fetchMealDetails(String mealId,Boolean isOffline);
    void onFavoriteClicked(MealDetail mealDetail);
    void checkIfFavorite(String mealId);
}

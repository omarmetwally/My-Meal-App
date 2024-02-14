package com.omarInc.mymeal.favorite.presenter;

import com.omarInc.mymeal.favorite.view.FavoriteView;

public interface FavoritePresenter {
    void loadMeals();
    void attachView(FavoriteView view);
    void detachView();

    void removeMeal(String mealId);
}

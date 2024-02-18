package com.omarInc.mymeal.favorite.presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.omarInc.mymeal.db.MealRepository;
import com.omarInc.mymeal.db.MealRepositoryImpl;
import com.omarInc.mymeal.favorite.view.FavoriteView;
import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.model.MealDetail;

import java.util.List;
import java.util.stream.Collectors;

public class FavoritePresenterImpl implements  FavoritePresenter{
    private FavoriteView view;
    private MealRepository mealRepository;

    public FavoritePresenterImpl(Context context) {
        this.mealRepository = MealRepositoryImpl.getInstance(context);
    }
    @Override
    public void loadMeals() {

        mealRepository.getAllMeals().observe(view.getLifecycleOwner(), mealDetails -> {
            List<Meal> simplifiedMeals = mealDetails.stream()
                    .map(mealDetail -> new Meal(mealDetail.getStrMeal(), mealDetail.getStrMealThumb(), mealDetail.getIdMeal()))
                    .collect(Collectors.toList());
            view.displayMeals(simplifiedMeals);
        });
    }

    @Override
    public void attachView(FavoriteView view) {
        this.view = view;

    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void removeMeal(String mealId) {
        mealRepository.deleteById(mealId);
        loadMeals();
    }


}

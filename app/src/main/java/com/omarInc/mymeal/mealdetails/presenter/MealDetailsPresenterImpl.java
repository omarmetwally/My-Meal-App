package com.omarInc.mymeal.mealdetails.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.omarInc.mymeal.db.MealRepository;
import com.omarInc.mymeal.mealdetails.view.MealDetailView;
import com.omarInc.mymeal.model.MealDetail;
import com.omarInc.mymeal.model.MealDetailResponse;
import com.omarInc.mymeal.network.MealRemoteDataSource;
import com.omarInc.mymeal.network.NetworkCallBack;

import java.util.Calendar;

public class MealDetailsPresenterImpl implements MealDetailsPresenter {
    private MealDetailView view;
    private MealRemoteDataSource dataSource;
    private MealRepository repository;

    public MealDetailsPresenterImpl(MealDetailView view, MealRemoteDataSource dataSource,MealRepository repository) {
        this.view = view;
        this.dataSource = dataSource;
        this.repository=repository;
    }

    @Override
    public void fetchMealDetails(String mealId,Boolean isOffline) {
        if(isOffline)
        {
            repository.getMealById(mealId).observe(view.getLifecycleOwner(), mealDetail -> {
                if (mealDetail != null) {
                    view.showMealDetails(mealDetail);
                } else {
                    view.showError("Meal details not found in the database.");
                }
            });




        }else {
            dataSource.getMealById(mealId, new NetworkCallBack<MealDetailResponse>() {
                @Override
                public void onSuccessResult(MealDetailResponse mealDetailResponse) {
                    view.showMealDetails(mealDetailResponse.getMeals().get(0)); // Assuming only one meal detail is returned
                }

                @Override
                public void onFailureResult(String errorMessage) {
                    view.showError(errorMessage);
                }
            });

        }

    }

    @Override
    public void onFavoriteClicked(MealDetail mealDetail) {

        repository.toggleFavorite(mealDetail);
    }

    @Override
    public void checkIfFavorite(String mealId) {
        repository.isFavorite(mealId).observe(view.getLifecycleOwner(), isFavorite -> {
            view.setFavoriteStatus(isFavorite);
        });
    }

    @Override
    public void scheduleMealForDate(MealDetail mealDetail, Calendar selectedDate) {
        repository.scheduleMeal(mealDetail, selectedDate.getTime());
    }

}

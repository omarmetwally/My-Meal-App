package com.omarInc.mymeal.mealdetails.presenter;

import com.omarInc.mymeal.mealdetails.view.MealDetailView;
import com.omarInc.mymeal.model.MealDetailResponse;
import com.omarInc.mymeal.network.MealRemoteDataSource;
import com.omarInc.mymeal.network.NetworkCallBack;

public class MealDetailsPresenterImpl implements MealDetailsPresenter {
    private MealDetailView view;
    private MealRemoteDataSource dataSource;

    public MealDetailsPresenterImpl(MealDetailView view, MealRemoteDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    @Override
    public void fetchMealDetails(String mealId) {
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

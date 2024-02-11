package com.omarInc.mymeal.recommendedMeals.presenter;

import com.omarInc.mymeal.model.MealsResponse;
import com.omarInc.mymeal.network.MealRemoteDataSource;
import com.omarInc.mymeal.network.NetworkCallBack;
import com.omarInc.mymeal.recommendedMeals.view.RecommendedMealsView;

public class RecommendedMealsPresenterImpl implements RecommendedMealsPresenter {
    private RecommendedMealsView view;
    private MealRemoteDataSource dataSource;

    public RecommendedMealsPresenterImpl(RecommendedMealsView view, MealRemoteDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    @Override
    public void fetchRecommendedMeals() {
        dataSource.getMealsByArea("Egyptian", new NetworkCallBack<MealsResponse>() {
            @Override
            public void onSuccessResult(MealsResponse response) {
                view.showRecommendedMeals(response.getMeals());
            }

            @Override
            public void onFailureResult(String errorMsg) {
                view.showError(errorMsg);
            }
        });
    }
}

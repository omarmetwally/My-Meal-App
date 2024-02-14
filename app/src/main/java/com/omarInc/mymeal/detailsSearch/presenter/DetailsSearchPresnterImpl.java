package com.omarInc.mymeal.detailsSearch.presenter;

import com.omarInc.mymeal.detailsSearch.view.MealsView;
import com.omarInc.mymeal.model.MealsResponse;
import com.omarInc.mymeal.network.MealRemoteDataSource;
import com.omarInc.mymeal.network.NetworkCallBack;

public class DetailsSearchPresnterImpl implements  DetailsSearchPresnter{
    private MealsView view;
    private MealRemoteDataSource dataSource;

    public DetailsSearchPresnterImpl(MealsView view, MealRemoteDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }


    @Override
    public void fetchCategoryMeals(String categoryName) {
        dataSource.getMealsByCategory(categoryName, new NetworkCallBack<MealsResponse>() {
            @Override
            public void onSuccessResult(MealsResponse response) {
                view.showMeals(response.getMeals());
            }

            @Override
            public void onFailureResult(String errorMsg) {
                view.showError(errorMsg);
            }
        });

    }



    @Override
    public void fetchIngredientsMeals(String ingredientName) {

        dataSource.getMealsByIngredient(ingredientName, new NetworkCallBack<MealsResponse>() {
            @Override
            public void onSuccessResult(MealsResponse response) {
                view.showMeals(response.getMeals());
            }

            @Override
            public void onFailureResult(String errorMsg) {
                view.showError(errorMsg);
            }
        });
    }

    @Override
    public void fetchCountriesMeals(String countryName) {

        dataSource.getMealsByArea(countryName, new NetworkCallBack<MealsResponse>() {
            @Override
            public void onSuccessResult(MealsResponse response) {
                view.showMeals(response.getMeals());
            }

            @Override
            public void onFailureResult(String errorMsg) {
                view.showError(errorMsg);
            }
        });
    }
}

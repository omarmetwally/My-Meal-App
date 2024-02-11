package com.omarInc.mymeal.search.presenter;

import com.omarInc.mymeal.model.MealsResponse;
import com.omarInc.mymeal.network.MealRemoteDataSource;
import com.omarInc.mymeal.network.NetworkCallBack;
import com.omarInc.mymeal.search.view.SearchingView;

public class SearchPresenterImpl implements SearchPresenter {
    private SearchingView searchingView;
    private MealRemoteDataSource mealRemoteDataSource;

    public SearchPresenterImpl(SearchingView view, MealRemoteDataSource dataSource) {
        this.searchingView = view;
        this.mealRemoteDataSource = dataSource;
    }

    @Override
    public void performSearch(final String query) {
        mealRemoteDataSource.searchMealsByName(query, new NetworkCallBack<MealsResponse>() {
            @Override
            public void onSuccessResult(MealsResponse response) {
                if (response != null && response.getMeals() != null) {
                    searchingView.showSearchResults(response.getMeals());
                } else {
                    searchingView.showError("No results found for \"" + query + "\"");
                }
            }

            @Override
            public void onFailureResult(String errorMsg) {
                searchingView.showError(errorMsg);
            }
        });
    }
}
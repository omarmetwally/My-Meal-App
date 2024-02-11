package com.omarInc.mymeal.ingredients.presenter;

import com.omarInc.mymeal.ingredients.view.IngredientsView;
import com.omarInc.mymeal.model.IngredientsResponse;
import com.omarInc.mymeal.network.MealRemoteDataSource;
import com.omarInc.mymeal.network.NetworkCallBack;

public class IngredientsPresenterImpl implements IngredientsPresenter{
    private IngredientsView view;
    private MealRemoteDataSource dataSource;

    public IngredientsPresenterImpl(IngredientsView view, MealRemoteDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    @Override
    public void getIngredients() {
        dataSource.getIngredients(new NetworkCallBack<IngredientsResponse>() {
            @Override
            public void onSuccessResult(IngredientsResponse response) {
                view.showIngredients(response.getIngredients());
            }

            @Override
            public void onFailureResult(String errorMsg) {
                view.showError(errorMsg);
            }
        });
    }
}
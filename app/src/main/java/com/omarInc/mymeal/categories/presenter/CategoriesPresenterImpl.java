package com.omarInc.mymeal.categories.presenter;


import com.omarInc.mymeal.categories.view.CategoriesView;
import com.omarInc.mymeal.model.CategoryResponse;
import com.omarInc.mymeal.network.MealRemoteDataSource;
import com.omarInc.mymeal.network.NetworkCallBack;

public class CategoriesPresenterImpl implements CategoriesPresenter {
    private CategoriesView categoriesView;

    private MealRemoteDataSource mealRemoteDataSource;

    public CategoriesPresenterImpl(CategoriesView view, MealRemoteDataSource dataSource) {
        this.categoriesView = view;
        this.mealRemoteDataSource = dataSource;
    }

    @Override
    public void getCategories() {
        mealRemoteDataSource.getCategories(new NetworkCallBack<CategoryResponse>() {
            @Override
            public void onSuccessResult(CategoryResponse response) {
                if (response != null && response.getCategories() != null) {
                    categoriesView.showCategories(response.getCategories());
                } else {
                    categoriesView.showError("No categories found");
                }
            }

            @Override
            public void onFailureResult(String errorMsg) {
                categoriesView.showError(errorMsg);
            }
        });
    }
}

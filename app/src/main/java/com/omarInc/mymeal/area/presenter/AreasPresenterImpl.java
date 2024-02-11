package com.omarInc.mymeal.area.presenter;

import com.omarInc.mymeal.area.model.AreasResponse;
import com.omarInc.mymeal.area.view.AreasView;
import com.omarInc.mymeal.network.MealRemoteDataSource;
import com.omarInc.mymeal.network.NetworkCallBack;

public class AreasPresenterImpl implements AreasPresenter {
    private AreasView view;
    private MealRemoteDataSource dataSource;

    public AreasPresenterImpl(AreasView view, MealRemoteDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    @Override
    public void getAreas() {
        dataSource.getAreas(new NetworkCallBack<AreasResponse>() {
            @Override
            public void onSuccessResult(AreasResponse response) {
                if (response != null && response.getAreas() != null) {
                    view.showAreas(response.getAreas());
                } else {
                    view.showError("No areas found.");
                }
            }

            @Override
            public void onFailureResult(String errorMsg) {
                view.showError(errorMsg);
            }
        });
    }

}
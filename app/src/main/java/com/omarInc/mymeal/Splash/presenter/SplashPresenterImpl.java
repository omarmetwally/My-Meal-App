package com.omarInc.mymeal.Splash.presenter;

import com.omarInc.mymeal.Splash.view.SplashView;
import com.omarInc.mymeal.sharedpreferences.SharedPreferencesDataSourceImpl;

public class SplashPresenterImpl implements SplashPresenter {
    private SplashView view;
    private SharedPreferencesDataSourceImpl sharedPreferences;

    public SplashPresenterImpl(SplashView view, SharedPreferencesDataSourceImpl sharedPreferences) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void decideNextPage() {
        if (sharedPreferences.getAuthToken() != null) {
            view.navigateToHome(sharedPreferences.getAuthToken());
        } else {
            view.navigateToLogin();
        }
    }
}

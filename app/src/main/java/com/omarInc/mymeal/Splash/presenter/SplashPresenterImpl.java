package com.omarInc.mymeal.Splash.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        String userId=sharedPreferences.getAuthToken();
        if ( userId!= null) {
//            checkUserExists(userId);
            view.navigateToHome();
        } else {
            view.navigateToLogin();
        }
    }
//    private void checkUserExists(String userId) {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null && user.getUid().equals(userId)) {
//            view.navigateToHome(userId);
//        } else {
//            view.navigateToLogin();
//        }
//    }
}

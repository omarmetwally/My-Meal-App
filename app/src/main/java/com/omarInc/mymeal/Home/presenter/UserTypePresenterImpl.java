package com.omarInc.mymeal.Home.presenter;

import com.omarInc.mymeal.Home.view.UserTypeView;
import com.omarInc.mymeal.sharedpreferences.SharedPreferencesDataSourceImpl;

public class UserTypePresenterImpl implements UserTypePresenter{

    private UserTypeView view;
    private SharedPreferencesDataSourceImpl sharedPreferences;

    public UserTypePresenterImpl(UserTypeView view,SharedPreferencesDataSourceImpl sharedPreferences) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void decideUserAction() {
        String userId=sharedPreferences.getAuthToken();
        if ( userId!= null) {
            view.userCanAccess();
        } else {
            view.userCanNotAccess();
        }

    }
}

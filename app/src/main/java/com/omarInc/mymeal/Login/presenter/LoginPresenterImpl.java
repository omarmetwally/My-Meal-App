package com.omarInc.mymeal.Login.presenter;

import android.content.Context;

import com.omarInc.mymeal.Login.view.LoginView;
import com.omarInc.mymeal.firebase.IFirebaseAuth;
import com.omarInc.mymeal.sharedpreferences.SharedPreferencesDataSourceImpl;


public class LoginPresenterImpl implements LoginPresenter {
    private LoginView view;
    private IFirebaseAuth authManager;
    private Context context;

    public LoginPresenterImpl(LoginView view, IFirebaseAuth authManager,Context context) {
        this.view = view;
        this.authManager = authManager;
        this.context=context;
    }

    @Override
    public void performLogin(String email, String password) {
        view.showLoading();
        authManager.signInWithEmail(email, password, new IFirebaseAuth.AuthResultCallback() {
            @Override
            public void onSuccess(String UserID) {
                view.hideLoading();
                view.onLoginSuccess( UserID);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideLoading();
                view.onLoginError(errorMessage);
            }
        });
    }

    @Override
    public void saveAuthToken(String token) {
        SharedPreferencesDataSourceImpl.getInstance(context).saveAuthToken(token);
    }
}

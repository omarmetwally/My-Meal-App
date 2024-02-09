package com.omarInc.mymeal.Login.presenter;

import com.omarInc.mymeal.Login.view.LoginView;
import com.omarInc.mymeal.firebase.IFirebaseAuth;


public class LoginPresenterImpl implements LoginPresenter {
    private LoginView view;
    private IFirebaseAuth authManager;

    public LoginPresenterImpl(LoginView view, IFirebaseAuth authManager) {
        this.view = view;
        this.authManager = authManager;
    }

    @Override
    public void performLogin(String email, String password) {
        view.showLoading();
        authManager.signInWithEmail(email, password, new IFirebaseAuth.AuthResultCallback() {
            @Override
            public void onSuccess() {
                view.hideLoading();
                view.onLoginSuccess();
            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideLoading();
                view.onLoginError(errorMessage);
            }
        });
    }
}

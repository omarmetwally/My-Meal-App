package com.omarInc.mymeal.Register.presenter;

import com.omarInc.mymeal.Register.view.RegisterView;
import com.omarInc.mymeal.firebase.IFirebaseAuth;

public class RegisterPresenterImpl implements RegisterPresenter {
    private RegisterView view;
    private IFirebaseAuth firebaseAuth;

    public RegisterPresenterImpl(RegisterView view, IFirebaseAuth firebaseAuth) {
        this.view = view;
        this.firebaseAuth = firebaseAuth;
    }


    @Override
    public void performRegister( String email, String password) {

        view.showLoading();
        firebaseAuth.signUpWithEmail(email, password, new IFirebaseAuth.AuthResultCallback() {
            @Override
            public void onSuccess() {
                view.hideLoading();
                view.onRegisterSuccess();
            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideLoading();
                view.onRegisterError(errorMessage);
            }
        });
    }
}

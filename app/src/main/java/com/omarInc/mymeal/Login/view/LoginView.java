package com.omarInc.mymeal.Login.view;

import androidx.lifecycle.LifecycleOwner;

public interface LoginView {
    void showLoading();
    void hideLoading();
    void onLoginSuccess(String userID,String email);
    void onLoginError(String message);
    boolean validateInput(String email, String password);
    boolean isValidEmail(String email);
    void showMessage(String message);


    void onGoogleLoginSuccess(String userID,String email);
    void onGoogleLoginError(String message);
    void onMealsFetchedAndStoredSuccessfully();
    LifecycleOwner getLifecycleOwner();
}

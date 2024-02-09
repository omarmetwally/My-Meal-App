package com.omarInc.mymeal.Login.view;

public interface LoginView {
    void showLoading();
    void hideLoading();
    void onLoginSuccess();
    void onLoginError(String message);
    boolean validateInput(String email, String password);
    boolean isValidEmail(String email);
    void showMessage(String message);
}

package com.omarInc.mymeal.Register.view;

public interface RegisterView {
    void showLoading();
    void hideLoading();
    void onRegisterSuccess();
    void onRegisterError(String message);
    boolean validateInput(String name, String email, String password, String confirmPassword);
    boolean isValidEmail(String email);
}

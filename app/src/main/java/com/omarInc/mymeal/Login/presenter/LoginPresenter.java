package com.omarInc.mymeal.Login.presenter;

public interface LoginPresenter {
    void performLogin(String email, String password);
    void saveAuthToken(String token);

    void performGoogleLogin_firebaseAuthWithGoogle(String idToken);

    void fetchAndStoreUserMeals(String userId);
    void fetchAndStoreUserScheduledMeals(String userId);

}

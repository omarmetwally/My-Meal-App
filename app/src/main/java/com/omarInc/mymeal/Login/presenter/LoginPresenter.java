package com.omarInc.mymeal.Login.presenter;

public interface LoginPresenter {
    void performLogin(String email, String password);
    void saveAuthToken(String token);

    void saveEmail(String email);

    void performGoogleLogin_firebaseAuthWithGoogle(String idToken,String email);

    void fetchAndStoreUserMeals(String userId);
    void fetchAndStoreUserScheduledMeals(String userId);

}

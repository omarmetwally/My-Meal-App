package com.omarInc.mymeal.sharedpreferences;



public interface ISharedPreferencesDataSource {
    void saveAuthToken(String token);
    void saveEmail(String email);
    String getEmail();
    String getAuthToken();
    void clearAuthToken();
}

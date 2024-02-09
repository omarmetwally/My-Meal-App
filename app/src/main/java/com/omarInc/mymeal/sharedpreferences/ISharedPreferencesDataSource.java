package com.omarInc.mymeal.sharedpreferences;



public interface ISharedPreferencesDataSource {
    void saveAuthToken(String token);
    String getAuthToken();
    void clearAuthToken();
}

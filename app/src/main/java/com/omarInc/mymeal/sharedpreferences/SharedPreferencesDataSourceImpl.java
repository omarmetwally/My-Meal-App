package com.omarInc.mymeal.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesDataSourceImpl implements ISharedPreferencesDataSource {
    private static final String PREFERENCES_FILE_NAME = "Auth";
    private static final String EMAIL = "email";
    private static final String AUTH_TOKEN_KEY = "auth_token";

    private static SharedPreferencesDataSourceImpl instance;
    private final SharedPreferences sharedPreferences;

    // Private constructor to prevent direct instantiation
    private SharedPreferencesDataSourceImpl(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    //  singleton instance
    public static synchronized SharedPreferencesDataSourceImpl getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesDataSourceImpl(context);
        }
        return instance;
    }

    @Override
    public void saveAuthToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AUTH_TOKEN_KEY, token);
        editor.apply();
    }

    @Override
    public void saveEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL, email);
        editor.apply();
    }

    @Override
    public String getEmail() {
        return sharedPreferences.getString(EMAIL, null);
    }

    @Override
    public String getAuthToken() {
        return sharedPreferences.getString(AUTH_TOKEN_KEY, null);
    }

    @Override
    public void clearAuthToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(AUTH_TOKEN_KEY);
        editor.apply();
    }
}
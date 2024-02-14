package com.omarInc.mymeal.Login.presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.omarInc.mymeal.Login.view.LoginView;
import com.omarInc.mymeal.db.MealRepository;
import com.omarInc.mymeal.firebase.IFirebaseAuth;
import com.omarInc.mymeal.model.MealDetail;
import com.omarInc.mymeal.sharedpreferences.SharedPreferencesDataSourceImpl;

import java.util.List;


public class LoginPresenterImpl implements LoginPresenter {
    private static final String TAG = "LoginPresenterImpl";
    private LoginView view;
    private IFirebaseAuth authManager;
    private Context context;
    private MealRepository mealRepository;

    public LoginPresenterImpl(LoginView view, IFirebaseAuth authManager, Context context, MealRepository mealRepository) {
        this.view = view;
        this.authManager = authManager;
        this.context = context;
        this.mealRepository = mealRepository; // Initialize mealRepository
    }

    @Override
    public void performLogin(String email, String password) {
        view.showLoading();
        authManager.signInWithEmail(email, password, new IFirebaseAuth.AuthResultCallback() {
            @Override
            public void onSuccess(String UserID) {
                view.hideLoading();
                view.onLoginSuccess(UserID);
                fetchAndStoreUserMeals(UserID);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideLoading();
                view.onLoginError(errorMessage);
            }
        });
    }

    @Override
    public void saveAuthToken(String token) {
        SharedPreferencesDataSourceImpl.getInstance(context).saveAuthToken(token);
    }

    @Override
    public void performGoogleLogin_firebaseAuthWithGoogle(String idToken) {
        // You'll need to implement the Google Sign-In logic here and call view.onGoogleLoginSuccess(userID) or view.onGoogleLoginError(errorMessage) accordingly.
        // For now, let's just call a method that starts the Google Sign-In process

        authManager.signInWithGoogle(idToken, new IFirebaseAuth.AuthResultCallback() {
            @Override
            public void onSuccess(String userId) {
                view.onGoogleLoginSuccess(userId);
                fetchAndStoreUserMeals(userId);
            }

            @Override
            public void onFailure(@NonNull String errorMessage) {

                view.onGoogleLoginError(errorMessage);
            }
        });

    }


    @Override
    public void fetchAndStoreUserMeals(String userId) {
        authManager.fetchMeals(userId, new IFirebaseAuth.DataFetchCallback<List<MealDetail>>() {
            @Override
            public void onSuccess(List<MealDetail> meals) {
                // Use a separate thread or AsyncTask for database operations
                new Thread(() -> {
                    mealRepository.insertAll(meals); // Assuming insertAll is a method in MealRepository for inserting a list of MealDetail
                    // Notify the UI thread about the success
                    if (view != null) {
                       view.onMealsFetchedAndStoredSuccessfully();
                    }
                }).start();
            }

            @Override
            public void onFailure(String message) {
                if (view != null) {
              //     view.showMessage(message);
                    Log.i(TAG, "gwa elfetchAndStoreUserMeals: "+message);
                }
            }
        });
    }
}
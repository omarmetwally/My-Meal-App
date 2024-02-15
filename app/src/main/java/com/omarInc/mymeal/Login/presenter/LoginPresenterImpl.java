package com.omarInc.mymeal.Login.presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.omarInc.mymeal.Login.view.LoginView;
import com.omarInc.mymeal.db.MealRepository;
import com.omarInc.mymeal.firebase.IFirebaseAuth;
import com.omarInc.mymeal.model.MealDetail;
import com.omarInc.mymeal.plan.model.ScheduledMeal;
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
        this.mealRepository = mealRepository;
    }

    @Override
    public void performLogin(String email, String password) {
        view.showLoading();
        authManager.signInWithEmail(email, password, new IFirebaseAuth.AuthResultCallback() {
            @Override
            public void onSuccess(String UserID) {
                view.hideLoading();
                view.onLoginSuccess(UserID,email);
                fetchAndStoreUserMeals(UserID);
                fetchAndStoreUserScheduledMeals(UserID);
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
    public void saveEmail(String email) {
        SharedPreferencesDataSourceImpl.getInstance(context).saveEmail(email);
    }

    @Override
    public void performGoogleLogin_firebaseAuthWithGoogle(String idToken,String email) {


        authManager.signInWithGoogle(idToken, new IFirebaseAuth.AuthResultCallback() {
            @Override
            public void onSuccess(String userId) {
                view.onGoogleLoginSuccess(userId,email);
                fetchAndStoreUserMeals(userId);
                fetchAndStoreUserScheduledMeals(userId);
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
                new Thread(() -> {
                    mealRepository.insertAll(meals);
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

    @Override
    public void fetchAndStoreUserScheduledMeals(String userId) {
        authManager.fetchSchedule(userId, new IFirebaseAuth.DataFetchCallback<List<ScheduledMeal>>() {
            @Override
            public void onSuccess(List<ScheduledMeal> data) {

                new Thread(() -> {
                    mealRepository.insertAllScheduledMeals(data);

                }).start();
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

}
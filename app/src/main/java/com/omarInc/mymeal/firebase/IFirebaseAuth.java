package com.omarInc.mymeal.firebase;

import androidx.annotation.NonNull;

import com.omarInc.mymeal.model.MealDetail;

import java.util.List;

public interface IFirebaseAuth {
    void signUpWithEmail(@NonNull String email, @NonNull String password, AuthResultCallback callback);
    void signInWithEmail(@NonNull String email, @NonNull String password, AuthResultCallback callback);
     void signInWithGoogle(String idToken, AuthResultCallback callback);
    void fetchMeals(String userId, DataFetchCallback<List<MealDetail>> callback);

    interface AuthResultCallback {
        void onSuccess(String userId);
        void onFailure(@NonNull String errorMessage);
    }
    public interface DataFetchCallback<T> {
        void onSuccess(T data);
        void onFailure(String message);
    }
}

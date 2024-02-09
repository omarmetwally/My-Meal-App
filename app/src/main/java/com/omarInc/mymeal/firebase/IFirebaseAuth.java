package com.omarInc.mymeal.firebase;

import androidx.annotation.NonNull;

public interface IFirebaseAuth {
    void signUpWithEmail(@NonNull String email, @NonNull String password, AuthResultCallback callback);
    void signInWithEmail(@NonNull String email, @NonNull String password, AuthResultCallback callback);

    interface AuthResultCallback {
        void onSuccess(String userId);
        void onFailure(@NonNull String errorMessage);
    }
}

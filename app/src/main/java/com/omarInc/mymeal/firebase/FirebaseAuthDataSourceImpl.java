package com.omarInc.mymeal.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthDataSourceImpl implements IFirebaseAuth {
    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthDataSourceImpl() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signUpWithEmail(@NonNull String email, @NonNull String password, AuthResultCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException() != null ? task.getException().getMessage() : "Unknown error occurred");
                    }
                });
    }

    @Override
    public void signInWithEmail(@NonNull String email, @NonNull String password, AuthResultCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException() != null ? task.getException().getMessage() : "Unknown error occurred");
                    }
                });
    }
}
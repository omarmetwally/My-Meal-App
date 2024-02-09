package com.omarInc.mymeal.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                        callback.onSuccess("");
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
                        // Retrieve the user ID of the signed-in user
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid(); // This is the user ID (token)
                            callback.onSuccess(userId); // Modify your callback to include the user ID
                        } else {
                            callback.onFailure("Failed to retrieve user information.");
                        }
                    } else {
                        callback.onFailure(task.getException() != null ? task.getException().getMessage() : "Unknown error occurred");
                    }
                });
    }

}
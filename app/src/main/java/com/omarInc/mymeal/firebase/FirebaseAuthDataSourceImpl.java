package com.omarInc.mymeal.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;
import com.omarInc.mymeal.model.MealDetail;

import java.util.ArrayList;
import java.util.List;

public class FirebaseAuthDataSourceImpl implements IFirebaseAuth {
    private final FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;

    public FirebaseAuthDataSourceImpl() {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
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

    @Override
    public void signInWithGoogle(String idToken, AuthResultCallback callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            callback.onSuccess(user.getUid());
                        } else {
                            callback.onFailure("Failed to retrieve user information.");
                        }
                    } else {
                        callback.onFailure(task.getException() != null ? task.getException().getMessage() : "Unknown error occurred");
                    }
                });
    }

    @Override
    public void fetchMeals(String userId, DataFetchCallback<List<MealDetail>> callback) {
        DatabaseReference mealsRef = database.getReference("users").child(userId).child("meals");

        mealsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<MealDetail> meals = new ArrayList<>();
                for (DataSnapshot mealSnapshot: dataSnapshot.getChildren()) {
                    MealDetail meal = mealSnapshot.getValue(MealDetail.class);
                    meals.add(meal);
                }
                if (!meals.isEmpty()) {
                    callback.onSuccess(meals);
                } else {
                    callback.onFailure("No meals found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }
}
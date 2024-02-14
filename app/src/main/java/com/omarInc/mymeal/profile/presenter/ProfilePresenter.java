package com.omarInc.mymeal.profile.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.omarInc.mymeal.db.MealRepository;
import com.omarInc.mymeal.firebase.FirebaseAuthDataSourceImpl;
import com.omarInc.mymeal.model.MealDetail;
import com.omarInc.mymeal.sharedpreferences.SharedPreferencesDataSourceImpl;

import java.util.List;

public class ProfilePresenter {
    private FirebaseAuthDataSourceImpl firebaseAuthDataSource;
    private MealRepository mealRepository;
    private Context context; // Assume this is passed in through the constructor or however you prefer

    public ProfilePresenter(FirebaseAuthDataSourceImpl firebaseAuthDataSource, MealRepository mealRepository, Context context) {
        this.firebaseAuthDataSource = firebaseAuthDataSource;
        this.mealRepository = mealRepository;
        this.context = context;
    }

    public void backupMealsToFirebase() {
        String userId = getUserIdFromPreferences();
        if (userId == null) {
            // Handle user not logged in or ID not found
            return;
        }

        LiveData<List<MealDetail>> mealsLiveData = mealRepository.getAllMeals();
        Observer<List<MealDetail>> mealObserver = new Observer<List<MealDetail>>() {
            @Override
            public void onChanged(List<MealDetail> meals) {
                uploadMealsToFirebase(userId, meals);
                // Now remove the observer to prevent further updates
                mealsLiveData.removeObserver(this);
            }
        };

        mealsLiveData.observeForever(mealObserver);

    }

    private String getUserIdFromPreferences() {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE);
//        return sharedPreferences.getString("auth_token", null);
        return SharedPreferencesDataSourceImpl.getInstance(context).getAuthToken();
    }

//    private void uploadMealsToFirebase(String userId, List<MealDetail> meals) {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("meals");
//        for (MealDetail meal : meals) {
//            // Assuming MealDetail has a unique ID or use push() for unique keys
//            databaseReference.child(meal.getIdMeal()).setValue(meal);
//        }
//    }

    private void uploadMealsToFirebase(String userId, List<MealDetail> meals) {
        DatabaseReference userMealsRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("meals");

        // Clear existing meals before uploading the new dataset
        userMealsRef.setValue(null).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Proceed with uploading the new dataset after successfully clearing the existing data
                for (MealDetail meal : meals) {
                    // Assuming MealDetail has a unique ID or use push() for unique keys
                    userMealsRef.child(meal.getIdMeal()).setValue(meal);
                }

                // Optionally, notify the user that the backup was successful after all meals are uploaded
                // This could be a Toast, Snackbar, or any other form of notification
            } else {
                // Handle the error in clearing the existing data
                // Log the error or show a message to the user
            }
        });
    }



    public   void clearDB()
    {
        mealRepository.clearAll();
    }
}

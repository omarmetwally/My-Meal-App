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
import com.omarInc.mymeal.plan.model.ScheduledMeal;
import com.omarInc.mymeal.sharedpreferences.SharedPreferencesDataSourceImpl;

import java.util.List;

public class ProfilePresenter {
    private FirebaseAuthDataSourceImpl firebaseAuthDataSource;
    private MealRepository mealRepository;

    private Context context;
    public ProfilePresenter(FirebaseAuthDataSourceImpl firebaseAuthDataSource, MealRepository mealRepository, Context context) {
        this.firebaseAuthDataSource = firebaseAuthDataSource;
        this.mealRepository = mealRepository;
        this.context = context;
    }

    public void backupMealsToFirebase() {
        String userId = getUserIdFromPreferences();
        if (userId == null) {
            return;
        }

        LiveData<List<MealDetail>> mealsLiveData = mealRepository.getAllMeals();
        Observer<List<MealDetail>> mealObserver = new Observer<List<MealDetail>>() {
            @Override
            public void onChanged(List<MealDetail> meals) {
                uploadMealsToFirebase(userId, meals);
                mealsLiveData.removeObserver(this);
            }
        };

        mealsLiveData.observeForever(mealObserver);

    }
    public void backupScheduleMeals(){
        String userId = getUserIdFromPreferences();
        if (userId == null) {
            return;
        }
        LiveData<List<ScheduledMeal>> mealsLiveData = mealRepository.getAllSchedules();
        Observer<List<ScheduledMeal>> mealObserver2 = new Observer<List<ScheduledMeal>>() {
            @Override
            public void onChanged(List<ScheduledMeal> meals) {
                uploadScheduleMealsToFirebase(userId, meals);
                mealsLiveData.removeObserver(this);
            }
        };

        mealsLiveData.observeForever(mealObserver2);

    }

    private void uploadScheduleMealsToFirebase(String userId, List<ScheduledMeal> meals) {
        DatabaseReference userMealsRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("schedule_meals");

        userMealsRef.setValue(null).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (ScheduledMeal meal : meals) {
                    userMealsRef.child(meal.getIdMeal()).setValue(meal);
                }

            } else {

            }
        });
    }

    private String getUserIdFromPreferences() {

        return SharedPreferencesDataSourceImpl.getInstance(context).getAuthToken();
    }


    private void uploadMealsToFirebase(String userId, List<MealDetail> meals) {
        DatabaseReference userMealsRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("meals");

        userMealsRef.setValue(null).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (MealDetail meal : meals) {
                    userMealsRef.child(meal.getIdMeal()).setValue(meal);
                }

            } else {

            }
        });
    }



    public   void clearDB()
    {
        mealRepository.clearAll();
    }
}

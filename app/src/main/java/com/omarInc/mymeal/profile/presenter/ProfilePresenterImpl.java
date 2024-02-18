package com.omarInc.mymeal.profile.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.db.MealRepository;
import com.omarInc.mymeal.firebase.FirebaseAuthDataSourceImpl;
import com.omarInc.mymeal.model.MealDetail;
import com.omarInc.mymeal.plan.model.ScheduledMeal;
import com.omarInc.mymeal.profile.view.ProfileView;
import com.omarInc.mymeal.sharedpreferences.SharedPreferencesDataSourceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImpl implements ProfilePresenter {
    private FirebaseAuthDataSourceImpl firebaseAuthDataSource;
    private MealRepository mealRepository;
    private ProfileView view;
    private CompositeDisposable disposables = new CompositeDisposable();

    private Context context;
    public ProfilePresenterImpl(ProfileView view,FirebaseAuthDataSourceImpl firebaseAuthDataSource, MealRepository mealRepository, Context context) {
        this.view=view;
        this.firebaseAuthDataSource = firebaseAuthDataSource;
        this.mealRepository = mealRepository;
        this.context = context;
    }

    @Override
    public void backupMealsToFirebase() {


        if (!isNetworkAvailable()) {
            showAlertForNoConnection();
            return;
        }
        String userId = getUserIdFromPreferences();
        if (userId == null) {
            return;
        }

        mealRepository.getAllMeals()
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> uploadMealsToFirebase(userId, meals),
                        throwable -> {}
                );



    }



    private void uploadMealsToFirebase(String userId, List<MealDetail> meals) {
        DatabaseReference userMealsRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("meals");

        userMealsRef.setValue(null).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (MealDetail meal : meals) {
                    userMealsRef.child(meal.getIdMeal()).setValue(meal);
                }

                showAlertSuccess();
            } else {

            }
        });

    }



    @Override
    public   void clearDB() {mealRepository.clearAll();}

    @Override
    public void FetchEmail() {
        view.showEmail(SharedPreferencesDataSourceImpl.getInstance(context).getEmail());
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }


    private void showAlertForNoConnection() {
        new AlertDialog.Builder(context)
                .setTitle(R.string.no_internet)
                .setMessage(R.string.CheckNetwork)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showAlertSuccess() {
        new AlertDialog.Builder(context)
                .setTitle(R.string.BackupSucc)
                .setMessage(R.string.ThankYou)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }



    @Override
    public void backupScheduleMeals(){

        if (!isNetworkAvailable()) {
            return;
        }

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
}

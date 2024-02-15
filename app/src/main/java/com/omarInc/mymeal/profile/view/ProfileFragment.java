package com.omarInc.mymeal.profile.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.omarInc.mymeal.R;
import com.omarInc.mymeal.db.MealRepository;
import com.omarInc.mymeal.db.MealRepositoryImpl;
import com.omarInc.mymeal.firebase.FirebaseAuthDataSourceImpl;
import com.omarInc.mymeal.profile.presenter.ProfilePresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    Button btnBackup,btnLogout;
    private ProfilePresenter profilePresenter;

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePresenter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBackup=view.findViewById(R.id.btnBackup);
        btnLogout=view.findViewById(R.id.btnLogout);
        btnBackup.setOnClickListener(v -> {
            profilePresenter.backupMealsToFirebase();
            profilePresenter.backupScheduleMeals();
        });
        btnLogout.setOnClickListener(v -> showLogoutConfirmationDialog());
    }

    private void initializePresenter() {
        // Initialize your presenter here
        Context context = getContext();
        if (context != null) {
            FirebaseAuthDataSourceImpl firebaseAuthDataSource = new FirebaseAuthDataSourceImpl();
            MealRepository mealRepository = MealRepositoryImpl.getInstance(context);
            profilePresenter = new ProfilePresenter(firebaseAuthDataSource, mealRepository, context);
        }
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> logout())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void logout() {
        SharedPreferences preferences = getActivity().getSharedPreferences("Auth", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
        profilePresenter.clearDB();

        getActivity().finishAffinity();
    }
}
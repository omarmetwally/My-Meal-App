package com.omarInc.mymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity2 extends AppCompatActivity {

    NavController navController;
    private BottomNavigationView bottomNavigationView;

    private SmoothBottomBar smoothBottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ////****** Bottom Nav el 2adema
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        bottomNavigationView = findViewById(R.id.bottomNavBar);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(destination.getId() == R.id.mealDetailsFragment) {
                // Hide bottom navigation bar when in MealDetailsFragment
                bottomNavigationView.setVisibility(View.GONE);
            } else {
                // Show bottom navigation bar for other destinations
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

    }



}
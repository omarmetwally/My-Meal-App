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
//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.fragmentContainerView);
//        navController = navHostFragment.getNavController();
//        bottomNavigationView = findViewById(R.id.bottomNavBar);
//
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);

         NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        View SmoothBottomBar = findViewById(R.id.bottomNavBar);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
        smoothBottomBar = findViewById(R.id.bottomNavBar);

        // Manually handling item clicks on the SmoothBottomBar
        smoothBottomBar.setOnItemSelectedListener(i -> {
            switch (i) {
                case 0:
                    // Navigate using the NavController
                    navController.navigate(R.id.homeFragment);
                    break;
                case 1:
                    navController.navigate(R.id.searchFragment);
                    break;
                case 2:
                    navController.navigate(R.id.savedFragment);
                    break;
                case 3:
                    navController.navigate(R.id.profileFragment);
                    break;

            }
            // return true to display the item as the selected item
        });
    }
}
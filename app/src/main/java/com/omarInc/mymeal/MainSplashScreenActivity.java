package com.omarInc.mymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

public class MainSplashScreenActivity extends AppCompatActivity {


    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

    }
    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
//    @Override
//    public void onBackPressed() {
//        if (!navController.navigateUp()) {
//            super.onBackPressed();
//        }
//    }
}

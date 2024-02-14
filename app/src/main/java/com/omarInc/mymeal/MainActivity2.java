package com.omarInc.mymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;



public class MainActivity2 extends AppCompatActivity {

    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                boolean noConnectivity = intent.getBooleanExtra(
                        ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
                );
                showNetworkBanner(noConnectivity);
                if (!noConnectivity) {
                    refreshCurrentFragment();
                }
            }
        }
    };
    NavController navController;
    private BottomNavigationView bottomNavigationView;

    private TextView btnTurnOnWifi,btnDismiss;
    private static final String TAG = "MainActivity2";
    private View banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //Check Network
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);
         btnTurnOnWifi = findViewById(R.id.buttonTurnOnWifi);
        btnDismiss= findViewById(R.id.btnDismiss);
        btnTurnOnWifi.setOnClickListener(view -> {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        });
        btnDismiss.setOnClickListener(view -> banner.setVisibility(View.GONE));

        ////****** Bottom Nav el 2adema
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        bottomNavigationView = findViewById(R.id.bottomNavBar);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(destination.getId() == R.id.mealDetailsFragment || destination.getId() == R.id.ingredientSearchFragment) {
                bottomNavigationView.setVisibility(View.GONE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

    }

    private void showNetworkBanner(boolean noConnectivity) {
        runOnUiThread(() -> {
            banner = findViewById(R.id.networkBanner);
            banner.setVisibility(noConnectivity ? View.VISIBLE : View.GONE);
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
    private void refreshCurrentFragment() {
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        int id = navController.getCurrentDestination().getId();
        navController.popBackStack(id, true);
        navController.navigate(id);
    }
}
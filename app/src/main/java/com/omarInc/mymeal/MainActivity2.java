package com.omarInc.mymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.omarInc.mymeal.Home.presenter.UserTypePresenter;
import com.omarInc.mymeal.Home.presenter.UserTypePresenterImpl;
import com.omarInc.mymeal.Home.view.UserTypeView;
import com.omarInc.mymeal.mealdetails.view.MealDetailsFragment;
import com.omarInc.mymeal.sharedpreferences.SharedPreferencesDataSourceImpl;


public class MainActivity2 extends AppCompatActivity implements UserTypeView {

    private boolean wasNetworkDisconnected = false;
    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                boolean noConnectivity = intent.getBooleanExtra(
                        ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
                );
                showNetworkBanner(noConnectivity);
                if (wasNetworkDisconnected && !noConnectivity) {
                    refreshCurrentFragment();
                    wasNetworkDisconnected = false;
                } else {
                    wasNetworkDisconnected = noConnectivity;
                }
            }
        }
    };
    NavController navController;
    private BottomNavigationView bottomNavigationView;

    private TextView btnTurnOnWifi, btnDismiss;
    private static final String TAG = "MainActivity2";
    private View banner;
    private UserTypePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        FirebaseMessaging.getInstance().subscribeToTopic("general")
//                .addOnCompleteListener(task -> {
//                    String msg = "Subscribed to topic 'general'!";
//                    if (!task.isSuccessful()) {
//                        msg = "Subscribe to topic 'general' failed";
//                    }
//                    Log.d(TAG, msg);
//                });

        presenter=new UserTypePresenterImpl(this, SharedPreferencesDataSourceImpl.getInstance(getApplication()));
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);
        btnTurnOnWifi = findViewById(R.id.buttonTurnOnWifi);
        btnDismiss = findViewById(R.id.btnDismiss);
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
            if (destination.getId() == R.id.mealDetailsFragment || destination.getId() == R.id.ingredientSearchFragment) {
                bottomNavigationView.setVisibility(View.GONE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

        presenter.decideUserAction();

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
        if (navController.getCurrentDestination() != null) {
            int id = navController.getCurrentDestination().getId();

            if (navController.getCurrentDestination().getId() == R.id.mealDetailsFragment|| navController.getCurrentDestination().getId() == R.id.ingredientSearchFragment) {
//                MealDetailsFragment.newInstance();
            } else {
                navController.popBackStack(id, true);
                navController.navigate(id);
            }
        }
    }

    @Override
    public void userCanNotAccess() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.savedFragment || itemId == R.id.planFragment || itemId == R.id.profileFragment) {
                showAlertForDisabledFeature();
                return false;
            } else {
                NavigationUI.onNavDestinationSelected(item, navController);
                return true;
            }
        });
    }


    private void showAlertForDisabledFeature() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.featureNotAvailable)
                .setMessage(R.string.signupTo)
                .setPositiveButton(R.string.OkaySign, (dialog, which) -> navigateToSignIn())
                .setNegativeButton(R.string.NoSignUp, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void navigateToSignIn() {
        navController.popBackStack();

        Intent intent = new Intent(getApplication(), MainSplashScreenActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void userCanAccess() {

    }
}
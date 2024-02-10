package com.omarInc.mymeal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeHostFragment extends Fragment{
   private NavController navController;
    private BottomNavigationView bottomNavigationView;
    NavHostFragment navHostFragment;


    public static HomeHostFragment newInstance(String param1, String param2) {
        HomeHostFragment fragment = new HomeHostFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
         navHostFragment = (NavHostFragment) activity.getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
       }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_host, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String userID=HomeHostFragmentArgs.fromBundle(getArguments()).getAuthID();



        navController = Navigation.findNavController(view.findViewById(R.id.fragmentContainerView));

        bottomNavigationView = view.findViewById(R.id.bottomNavBar);

       NavigationUI.setupWithNavController(bottomNavigationView, navController);


//
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            AppCompatActivity activity = (AppCompatActivity) getActivity();
//            int id = item.getItemId();
//            if (id == R.id.homeFragment) {
//              //  navController.navigate(R.id.homeFragment);
//               activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new HomeFragment()).commit();
//
//                showToast("Home selected");
//            } else if (id == R.id.searchFragment) {
//             //   navController.navigate(R.id.searchFragment);
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new SearchFragment()).commit();
//
//                showToast("Search selected");
//            } else if (id == R.id.savedFragment) {
//             //   navController.navigate(R.id.savedFragment);
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new SavedFragment()).commit();
//
//                showToast("Saved selected");
//            } else if (id == R.id.profileFragment) {
//             //   navController.navigate(R.id.profileFragment);
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new ProfileFragment()).commit();
//
//                showToast("Profile selected");
//            } else {
//                return false;
//            }
//            return true;
//        });


    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.homeFragment)
        {
            showToast("Home");
        }


        return super.onOptionsItemSelected(item);
    }
}
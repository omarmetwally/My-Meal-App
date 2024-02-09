package com.omarInc.mymeal.Splash.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omarInc.mymeal.R;
import com.omarInc.mymeal.sharedpreferences.SharedPreferencesDataSourceImpl;

public class SplashFragment extends Fragment {
    private static final int SPLASH_TIME_OUT = 3000;
    public static SplashFragment newInstance(String param1, String param2) {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
                new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                NavController navController = Navigation.findNavController(view);

//                navController.popBackStack();

                if (SharedPreferencesDataSourceImpl.getInstance(requireContext()).getAuthToken() != null) {
                    // If token exists, navigate to HomeFragment
                    SplashFragmentDirections.ActionSplashFragmentToHomeFragment action=
                            SplashFragmentDirections.
                                    actionSplashFragmentToHomeFragment("");
                    action.setAuthID(SharedPreferencesDataSourceImpl.getInstance(requireContext()).getAuthToken());
                    navController.navigate(action);
                } else {
                    // If not, navigate to LoginFragment
                    navController.navigate(R.id.action_splashFragment_to_loginFragment);
                }


            }
        }, SPLASH_TIME_OUT);

    }
}
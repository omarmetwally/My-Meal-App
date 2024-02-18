package com.omarInc.mymeal.Splash.view;

import android.content.Intent;
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
import android.widget.Toast;

import com.omarInc.mymeal.MainActivity2;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.Splash.presenter.SplashPresenter;
import com.omarInc.mymeal.Splash.presenter.SplashPresenterImpl;
import com.omarInc.mymeal.sharedpreferences.SharedPreferencesDataSourceImpl;

public class SplashFragment extends Fragment implements SplashView {
    private static final int SPLASH_TIME_OUT = 3000;
    private SplashPresenter presenter;

    public static SplashFragment newInstance(String param1, String param2) {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashPresenterImpl(this, SharedPreferencesDataSourceImpl.getInstance(requireContext()));

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

                presenter.decideNextPage();

            }
        }, SPLASH_TIME_OUT);

    }

    @Override
    public void navigateToHome() {


        Intent i = new Intent(getActivity(), MainActivity2.class);
        startActivity(i);
       getActivity().finish();

    }

    @Override
    public void navigateToLogin() {
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_splashFragment_to_loginFragment);
    }
}
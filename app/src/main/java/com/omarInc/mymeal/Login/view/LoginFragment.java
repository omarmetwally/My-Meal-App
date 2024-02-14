package com.omarInc.mymeal.Login.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.omarInc.mymeal.Login.presenter.LoginPresenter;
import com.omarInc.mymeal.Login.presenter.LoginPresenterImpl;
import com.omarInc.mymeal.MainActivity2;
import com.omarInc.mymeal.MainSplashScreenActivity;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.db.MealRepositoryImpl;
import com.omarInc.mymeal.firebase.FirebaseAuthDataSourceImpl;
import com.omarInc.mymeal.firebase.IFirebaseAuth;


public class LoginFragment extends Fragment implements OnLoginClick,LoginView {


    private  final int RC_SIGN_IN = 9001;
    TextView txtRegister,txtForgetPassword;
    NavController navController;
    Button btnSkip;
    TextInputEditText emailEditText,passwordEditText;
    CircularProgressButton btnLogin;
    private LoginPresenter presenter;
    private GoogleSignInClient mGoogleSignInClient;

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Get the web client ID from strings.xml
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        SignInButton signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(v -> startGoogleSignIn());

        txtForgetPassword=view.findViewById(R.id.txtForgetPassword);
        btnSkip=view.findViewById(R.id.btnSkip);
        emailEditText=view.findViewById(R.id.emailLoginEditTetx);
        passwordEditText=view.findViewById(R.id.passwordLoginEditText);
        btnLogin=view.findViewById(R.id.btnLogin);
        txtRegister=view.findViewById(R.id.txtRegisterNow);
        IFirebaseAuth authManager = new FirebaseAuthDataSourceImpl();
        presenter = new LoginPresenterImpl(this, authManager, getContext(), MealRepositoryImpl.getInstance(getActivity()));
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (validateInput(email, password)) {
                    onLoginClickListener(email,password);
                }

            }
        });

    }

    @Override
    public void onLoginClickListener(String email, String password) {

      //  Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();
        presenter.performLogin(email, password);
    }

    @Override
    public void showLoading() {
        btnLogin.startAnimation();
    }

    @Override
    public void hideLoading() {
        btnLogin.stopAnimation();
        btnLogin.revertAnimation();
        btnLogin.dispose();

    }

    @Override
    public void onLoginSuccess(String userID) {
        Snackbar.make(getView(), "Login Successfully"+userID, BaseTransientBottomBar.LENGTH_LONG).show();

        presenter.saveAuthToken(userID);
//        LoginFragmentDirections.ActionLoginFragmentToHomeFragment action =
//                LoginFragmentDirections.actionLoginFragmentToHomeFragment("");
//        action.setAuthID(userID);
//        navController.navigate(action);
        Intent i = new Intent(getActivity(), MainActivity2.class);
        startActivity(i);
        getActivity().finish();


    }

    @Override
    public void onLoginError(String message) {
        Snackbar.make(getView(), message, BaseTransientBottomBar.LENGTH_LONG).show();


    }

    @Override
    public boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            emailEditText.setError("Email cannot be empty");
            showMessage("Email cannot be empty");
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Password cannot be empty");
            showMessage("Password cannot be empty");
            return false;
        }
        if (!isValidEmail(email)) {
            emailEditText.setError("Invalid email format");
            showMessage("Invalid email format");
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void onGoogleLoginSuccess(String userID) {

        Snackbar.make(getView(), "Login Successfully"+userID, BaseTransientBottomBar.LENGTH_LONG).show();

        presenter.saveAuthToken(userID);

        Intent i = new Intent(getActivity(), MainActivity2.class);
        startActivity(i);
        getActivity().finish();

    }

    @Override
    public void onGoogleLoginError(String message) {
        Snackbar.make(getView(), message, BaseTransientBottomBar.LENGTH_LONG).show();

    }

    @Override
    public void onMealsFetchedAndStoredSuccessfully() {


    }

    public void startGoogleSignIn() {
        // Here you will start the Google Sign-In process
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign-In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                presenter.performGoogleLogin_firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign-In failed
             //   Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    @Override
    public LifecycleOwner getLifecycleOwner() {
        return getViewLifecycleOwner();
    }
}
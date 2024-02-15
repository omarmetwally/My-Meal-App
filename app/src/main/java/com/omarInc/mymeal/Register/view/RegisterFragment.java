package com.omarInc.mymeal.Register.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.Register.presenter.RegisterPresenterImpl;
import com.omarInc.mymeal.firebase.FirebaseAuthDataSourceImpl;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements OnRegisterClick, RegisterView {


    NavController navController;
    ImageView imgBack;
    TextInputEditText nameEditTet,emailEditText,passwordEditText,confirmPasswordEditText;
    CircularProgressButton btnRegister;
    private RegisterPresenterImpl presenter;
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(view);
        imgBack=view.findViewById(R.id.imgBackInRegister);
        btnRegister=view.findViewById(R.id.btnRegister);
        nameEditTet=view.findViewById(R.id.nameEditText);
        emailEditText=view.findViewById(R.id.emailRegisterEditText);
        passwordEditText=view.findViewById(R.id.passwordRegisterEditText);
        confirmPasswordEditText=view.findViewById(R.id.confirmPasswordRegisterEditText);
        presenter = new RegisterPresenterImpl(this, new FirebaseAuthDataSourceImpl());
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditTet.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (validateInput(name, email, password,confirmPassword)) {
                    onRegisterClickListener(email,password);
                }

            }
        });

    }

    @Override
    public void showLoading() {
        btnRegister.startAnimation();
    }

    @Override
    public void hideLoading() {

        btnRegister.stopAnimation();
        btnRegister.revertAnimation();
    }

    @Override
    public void onRegisterSuccess() {

        Snackbar.make(getView(), R.string.registerSuccessfully, BaseTransientBottomBar.LENGTH_LONG).show();
        navController.popBackStack();
    }

    @Override
    public void onRegisterError(String message) {

        Snackbar.make(getView(), message, BaseTransientBottomBar.LENGTH_LONG).show();
    }

    @Override
    public boolean validateInput(String name, String email, String password, String confirmPassword) {
        if (email.isEmpty()) {
            emailEditText.setError(String.valueOf(R.string.emailEmpty));
           // showMessage("Email cannot be empty");
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError(String.valueOf(R.string.passwordEmpty));
           // showMessage("Password cannot be empty");
            return false;
        }
        if (!isValidEmail(email)) {
            emailEditText.setError(String.valueOf(R.string.invalidEmail));
          //  showMessage("Invalid email format");
            return false;
        }
        if(!password.equals(confirmPassword))
        {
         confirmPasswordEditText.setError(String.valueOf(R.string.passwordMatch));
        }
        return true;
    }

    @Override
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    @Override
    public void onRegisterClickListener(String email, String password) {

        presenter.performRegister( email, password);
    }
}
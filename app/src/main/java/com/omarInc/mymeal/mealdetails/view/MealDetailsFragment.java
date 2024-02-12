package com.omarInc.mymeal.mealdetails.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.mealdetails.presenter.MealDetailsPresenter;
import com.omarInc.mymeal.mealdetails.presenter.MealDetailsPresenterImpl;
import com.omarInc.mymeal.model.MealDetail;
import com.omarInc.mymeal.network.MealRemoteDataSourceImpl;


public class MealDetailsFragment extends Fragment implements MealDetailView {

    private ImageView foodCoverImg,btnBackImage;
    private TextView foodCategoryTxt, foodAreaTxt, foodTitleTxt, foodDescTxt, ingredientsTxt, measureTxt;
    private MealDetailsPresenter presenter;

    public MealDetailsFragment() {
        // Required empty public constructor
    }


    public static MealDetailsFragment newInstance() {
        MealDetailsFragment fragment = new MealDetailsFragment();
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
        return inflater.inflate(R.layout.fragment_meal_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        foodCoverImg = view.findViewById(R.id.foodCoverImg);
        foodCategoryTxt = view.findViewById(R.id.foodCategoryTxt);
        foodAreaTxt = view.findViewById(R.id.foodAreaTxt);
        foodTitleTxt = view.findViewById(R.id.foodTitleTxt);
        foodDescTxt = view.findViewById(R.id.foodDescTxt);
        ingredientsTxt = view.findViewById(R.id.ingredientsTxt);
        measureTxt = view.findViewById(R.id.measureTxt);
        btnBackImage=view.findViewById(R.id.detailBack);

        presenter = new MealDetailsPresenterImpl(this, MealRemoteDataSourceImpl.getInstance());
        if (getArguments() != null) {
            String mealID = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealID();
            presenter.fetchMealDetails(mealID);


            Toast.makeText(getActivity(), "Meal ID "+mealID, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMealDetails(MealDetail mealDetail) {

        Glide.with(this).load(mealDetail.getStrMealThumb()).into(foodCoverImg);
        foodCategoryTxt.setText(mealDetail.getStrCategory());
        foodAreaTxt.setText(mealDetail.getStrArea());
        foodTitleTxt.setText(mealDetail.getStrMeal());
        // Set the instructions or description
        foodDescTxt.setText(mealDetail.getStrInstructions());
        // Combine all ingredients into one string and set it to ingredientsTxt
        ingredientsTxt.setText(mealDetail.getStrIngredient1()+"\n"+mealDetail.getStrIngredient2());
        measureTxt.setText(mealDetail.getStrMeasure1()+"\n"+mealDetail.getStrMeasure2());
        btnBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });
    }

    @Override
    public void showError(String errorMessage) {


    }
}
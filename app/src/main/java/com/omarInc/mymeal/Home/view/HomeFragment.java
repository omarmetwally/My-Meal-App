package com.omarInc.mymeal.Home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.omarInc.mymeal.area.model.Area;
import com.omarInc.mymeal.area.presenter.AreasPresenter;
import com.omarInc.mymeal.area.presenter.AreasPresenterImpl;
import com.omarInc.mymeal.area.view.AreasAdapter;
import com.omarInc.mymeal.area.view.AreasView;
import com.omarInc.mymeal.categories.presenter.CategoriesPresenter;
import com.omarInc.mymeal.categories.presenter.CategoriesPresenterImpl;

import com.omarInc.mymeal.R;
import com.omarInc.mymeal.categories.view.CategoriesAdapter;
import com.omarInc.mymeal.categories.view.CategoriesView;
import com.omarInc.mymeal.ingredients.presenter.IngredientsPresenter;
import com.omarInc.mymeal.ingredients.presenter.IngredientsPresenterImpl;
import com.omarInc.mymeal.ingredients.view.IngredientsAdapter;
import com.omarInc.mymeal.ingredients.view.IngredientsView;
import com.omarInc.mymeal.model.Category;

import com.omarInc.mymeal.model.Ingredient;
import com.omarInc.mymeal.model.IngredientsManager;
import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.network.MealRemoteDataSourceImpl;
import com.omarInc.mymeal.recommendedMeals.presenter.RecommendedMealsPresenter;
import com.omarInc.mymeal.recommendedMeals.presenter.RecommendedMealsPresenterImpl;
import com.omarInc.mymeal.model.MealsAdapter;
import com.omarInc.mymeal.recommendedMeals.view.RecommendedMealsView;
import com.omarInc.mymeal.recommendedMeals.view.ViewPagerStack;
import com.stack.viewpager.OrientedViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements CategoriesView, IngredientsView, AreasView, RecommendedMealsView {

    private RecyclerView categoriesRecyclerView, ingredientsRecyclerView, areaRecyclerView;
    private RecyclerView recommendedRecyclerView;
    private CategoriesPresenter categoriesPresenter;
    private IngredientsPresenter ingredientsPresenter;
    private AreasPresenter areasPresenter;
    private RecommendedMealsPresenter recommendedMealsPresenter;
    private CategoriesAdapter categoriesAdapter;
    private IngredientsAdapter ingredientsAdapter;
    private AreasAdapter areasAdapter;
    private MealsAdapter mealsAdapter;
    private static final String TAG = "HomeFragment";
    ShimmerFrameLayout shimmerFrameLayout;
    private TextView seeMoreTextView;

    ViewPager2 viewPager2;


    private OrientedViewPager mOrientedViewPager;
    private List<Fragment> mFragments = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate: ");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        seeMoreTextView = view.findViewById(R.id.seeMoreTxt);

        categoriesInit(view);


        ingredientsInit(view);


        areaInit(view);


        recommendedInit(view);


    }

    private void recommendedInit(@NonNull View view) {

        recommendedMealsPresenter = new RecommendedMealsPresenterImpl(this, MealRemoteDataSourceImpl.getInstance(getActivity()));
        recommendedMealsPresenter.fetchRecommendedMeals();
        mealsAdapter = new MealsAdapter(getContext(), new ArrayList<>(), mealId -> {


            HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                    HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealId, false);
            NavHostFragment.findNavController(HomeFragment.this).navigate(action);

        }, R.layout.stack_meal_item);


        ViewPager2 viewPager21 = view.findViewById(R.id.viewPager);

        viewPager21.setPageTransformer(new ViewPagerStack());


        viewPager21.setOffscreenPageLimit(2);
        viewPager21.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        viewPager21.setAdapter(mealsAdapter);

    }

    private void areaInit(@NonNull View view) {
        areasPresenter = new AreasPresenterImpl(this, MealRemoteDataSourceImpl.getInstance(getActivity()));
        areasPresenter.getAreas();
        areaRecyclerView = view.findViewById(R.id.areaRecView);
        areaRecyclerView.setHasFixedSize(true);
        int numberOfColumns = 3;

        GridLayoutManager ingredientsLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        ingredientsLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        areaRecyclerView.setLayoutManager(ingredientsLayoutManager);

        areasAdapter = new AreasAdapter(getContext(), new ArrayList<>(), areaName -> {

            HomeFragmentDirections.ActionHomeFragmentToIngredientSearchFragment action =
                    HomeFragmentDirections.actionHomeFragmentToIngredientSearchFragment(null, 3, areaName);
            NavHostFragment.findNavController(HomeFragment.this).navigate(action);

        });
        areaRecyclerView.setAdapter(areasAdapter);
    }

    private void ingredientsInit(@NonNull View view) {
        ingredientsPresenter = new IngredientsPresenterImpl(this, MealRemoteDataSourceImpl.getInstance(getActivity()));
        ingredientsPresenter.getIngredients();
        ingredientsRecyclerView = view.findViewById(R.id.ingredientdRecView);
        ingredientsRecyclerView.setHasFixedSize(true);
        int numberOfColumns = 2;
        GridLayoutManager ingredientsLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        ingredientsLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ingredientsRecyclerView.setLayoutManager(ingredientsLayoutManager);

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_scale_in);
        ingredientsRecyclerView.setLayoutAnimation(animation);

        ingredientsAdapter = new IngredientsAdapter(getContext(), new ArrayList<>(), false, ingredientId -> {
            HomeFragmentDirections.ActionHomeFragmentToIngredientSearchFragment action =
                    HomeFragmentDirections.actionHomeFragmentToIngredientSearchFragment(null, 2, ingredientId);
            NavHostFragment.findNavController(HomeFragment.this).navigate(action);
        });
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
    }

    private void categoriesInit(@NonNull View view) {
        categoriesPresenter = new CategoriesPresenterImpl(this, MealRemoteDataSourceImpl.getInstance(getActivity()));
        categoriesPresenter.getCategories();

        categoriesRecyclerView = view.findViewById(R.id.categoryRecView);
        categoriesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager categoriesLayoutManager = new LinearLayoutManager(getActivity());
        categoriesLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_scale_in);
        categoriesRecyclerView.setLayoutAnimation(animation);

        //  recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoriesAdapter = new CategoriesAdapter(getContext(), new ArrayList<>(), categoryId -> {
            HomeFragmentDirections.ActionHomeFragmentToIngredientSearchFragment action =
                    HomeFragmentDirections.actionHomeFragmentToIngredientSearchFragment(null, 1, categoryId);
            NavHostFragment.findNavController(HomeFragment.this).navigate(action);
        });
        categoriesRecyclerView.setAdapter(categoriesAdapter);
    }


    @Override
    public void showCategories(List<Category> categories) {
        categoriesAdapter.setCategories(categories);

        if (categories != null && !categories.isEmpty()) {
            categoriesRecyclerView.scheduleLayoutAnimation();
        }

    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {

        ingredientsAdapter.setIngredients(ingredients);
        shimmerFrameLayout.hideShimmer();
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);

        if (ingredients != null && !ingredients.isEmpty()) {
            ingredientsRecyclerView.scheduleLayoutAnimation();
        }


        seeMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String ingredientsJson = gson.toJson(ingredients);

                Log.i(TAG, "ingredientsJson: " + ingredientsJson);
                HomeFragmentDirections.ActionHomeFragmentToIngredientSearchFragment action =
                        HomeFragmentDirections.actionHomeFragmentToIngredientSearchFragment(ingredientsJson, 0, "");
                NavHostFragment.findNavController(HomeFragment.this).navigate(action);
            }
        });
    }

    @Override
    public void showAreas(List<Area> areas) {

        areasAdapter.setAreas(areas);
    }

    @Override
    public void showRecommendedMeals(List<Meal> meals) {

        mealsAdapter.setMeals(meals);
        mealsAdapter.notifyDataSetChanged();


    }

    @Override
    public void showError(String error) {

    }


}
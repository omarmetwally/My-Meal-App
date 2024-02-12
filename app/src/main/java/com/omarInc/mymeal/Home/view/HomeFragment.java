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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.facebook.shimmer.ShimmerFrameLayout;
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
import com.omarInc.mymeal.mealdetails.view.MealDetailsFragment;
import com.omarInc.mymeal.model.Category;

import com.omarInc.mymeal.model.Ingredient;
import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.network.MealRemoteDataSourceImpl;
import com.omarInc.mymeal.recommendedMeals.presenter.RecommendedMealsPresenter;
import com.omarInc.mymeal.recommendedMeals.presenter.RecommendedMealsPresenterImpl;
import com.omarInc.mymeal.recommendedMeals.view.MealsAdapter;
import com.omarInc.mymeal.recommendedMeals.view.RecommendedMealsView;
import com.stack.viewpager.OrientedViewPager;
import com.stack.viewpager.transformer.VerticalStackTransformer;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements CategoriesView, IngredientsView , AreasView, RecommendedMealsView {

    private RecyclerView categoriesRecyclerView,ingredientsRecyclerView,areaRecyclerView;
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

    ViewPager2 viewPager2;




    private OrientedViewPager mOrientedViewPager;
    //private StackFragmentAdapter mContentFragmentAdapter;
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

        categoriesInit(view);


        ingredientsInit(view);


        areaInit(view);


        recommendedInit(view);


    }

    private void recommendedInit(@NonNull View view) {

      recommendedMealsPresenter = new RecommendedMealsPresenterImpl(this, MealRemoteDataSourceImpl.getInstance());
        recommendedMealsPresenter.fetchRecommendedMeals();

        recommendedRecyclerView = view.findViewById(R.id.recommendedRecView);
        recommendedRecyclerView.setHasFixedSize(true);
        LinearLayoutManager recommendedLayoutManager = new LinearLayoutManager(getActivity());
        recommendedLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recommendedRecyclerView.setLayoutManager(recommendedLayoutManager);

        //  recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mealsAdapter = new MealsAdapter(getContext(), new ArrayList<>(),mealId -> {


            HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action=
                    HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealId);
            NavHostFragment.findNavController(HomeFragment.this).navigate(action);

        });
        recommendedRecyclerView.setAdapter(mealsAdapter);





        //////////////////*****************

//        recommendedMealsPresenter = new RecommendedMealsPresenterImpl(this, MealRemoteDataSourceImpl.getInstance());
//        mOrientedViewPager = view.findViewById(R.id.view_pager);
//        mOrientedViewPager.setOrientation(OrientedViewPager.Orientation.HORIZONTAL);
//        mOrientedViewPager.setOffscreenPageLimit(1);
//        mOrientedViewPager.setPageTransformer(true, new VerticalStackTransformer(getContext()));
//
//        // Fetch recommended meals and then set up the ViewPager adapter
//        recommendedMealsPresenter.fetchRecommendedMeals();


//        viewPager2 = view.findViewById(R.id.viewPager);
//
//        // Initialize your adapter with an empty list or pre-fetched data
//        mealsAdapter = new MealsAdapter(getContext(), new ArrayList<>());
//
//        // Set the adapter to the ViewPager2
//
//        viewPager2.setAdapter(mealsAdapter);
//
//
//        // Fetch recommended meals
//        recommendedMealsPresenter = new RecommendedMealsPresenterImpl(this, MealRemoteDataSourceImpl.getInstance());
//        recommendedMealsPresenter.fetchRecommendedMeals();



//        viewPager2 = view.findViewById(R.id.viewPager);
//
//        // Initialize your adapter with an empty list or pre-fetched data
//        mealsAdapter = new MealsAdapter(getContext(), new ArrayList<>());
//
//        // Set the adapter to the ViewPager2
//        viewPager2.setAdapter(mealsAdapter);
//
//        // Fetch recommended meals and update adapter
//        recommendedMealsPresenter = new RecommendedMealsPresenterImpl(this, MealRemoteDataSourceImpl.getInstance());
//        recommendedMealsPresenter.fetchRecommendedMeals();

    }

    private void areaInit(@NonNull View view) {
        areasPresenter = new AreasPresenterImpl(this, MealRemoteDataSourceImpl.getInstance());
        areasPresenter.getAreas();
        areaRecyclerView = view.findViewById(R.id.areaRecView);
        areaRecyclerView.setHasFixedSize(true);

        int numberOfColumns = 3; // For example, 2 columns

        GridLayoutManager ingredientsLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        ingredientsLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        areaRecyclerView.setLayoutManager(ingredientsLayoutManager);

        areasAdapter = new AreasAdapter(getContext(), new ArrayList<>());
        areaRecyclerView.setAdapter(areasAdapter);
    }

    private void ingredientsInit(@NonNull View view) {
        ingredientsPresenter = new IngredientsPresenterImpl(this, MealRemoteDataSourceImpl.getInstance());
        ingredientsPresenter.getIngredients();
        ingredientsRecyclerView = view.findViewById(R.id.ingredientdRecView);
        ingredientsRecyclerView.setHasFixedSize(true);
        // Specify the number of columns in the grid
        int numberOfColumns = 2; // For example, 2 columns

        GridLayoutManager ingredientsLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        ingredientsLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ingredientsRecyclerView.setLayoutManager(ingredientsLayoutManager);

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_scale_in);
        ingredientsRecyclerView.setLayoutAnimation(animation);

        ingredientsAdapter = new IngredientsAdapter(getContext(), new ArrayList<>());
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
    }

    private void categoriesInit(@NonNull View view) {
        categoriesPresenter = new CategoriesPresenterImpl(this, MealRemoteDataSourceImpl.getInstance());
        categoriesPresenter.getCategories();

        categoriesRecyclerView = view.findViewById(R.id.categoryRecView);
        categoriesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager categoriesLayoutManager = new LinearLayoutManager(getActivity());
        categoriesLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_scale_in);
        categoriesRecyclerView.setLayoutAnimation(animation);

        //  recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoriesAdapter = new CategoriesAdapter(getContext(), new ArrayList<>());
        categoriesRecyclerView.setAdapter(categoriesAdapter);
    }


    @Override
    public void showCategories(List<Category> categories) {
        categoriesAdapter.setCategories(categories);

        if(categories != null && !categories.isEmpty()) {
            categoriesRecyclerView.scheduleLayoutAnimation(); // Trigger animation after setting data
        }

    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {

        ingredientsAdapter.setIngredients(ingredients);
        shimmerFrameLayout.hideShimmer();
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);

        if(ingredients != null && !ingredients.isEmpty()) {
            ingredientsRecyclerView.scheduleLayoutAnimation(); // Trigger animation after setting data
        }
    }

    @Override
    public void showAreas(List<Area> areas) {

        areasAdapter.setAreas(areas);
    }

    @Override
    public void showRecommendedMeals(List<Meal> meals) {

//        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager(), meals);
//        mOrientedViewPager.setAdapter(adapter);


       mealsAdapter.setMeals(meals);
        mealsAdapter.notifyDataSetChanged();


    }

    @Override
    public void showError(String error) {

    }


}
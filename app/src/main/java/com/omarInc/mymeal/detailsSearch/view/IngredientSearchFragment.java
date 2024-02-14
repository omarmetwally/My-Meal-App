package com.omarInc.mymeal.detailsSearch.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;

import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.categories.view.CategoriesAdapter;
import com.omarInc.mymeal.detailsSearch.presenter.DetailsSearchPresnter;
import com.omarInc.mymeal.detailsSearch.presenter.DetailsSearchPresnterImpl;
import com.omarInc.mymeal.ingredients.view.IngredientsAdapter;
import com.omarInc.mymeal.model.Ingredient;
import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.model.MealsAdapter;
import com.omarInc.mymeal.network.MealRemoteDataSourceImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class IngredientSearchFragment extends Fragment implements MealsView{


    private static final String TAG = "IngredientSearchFragmen";
    private ImageView backImage;
    private TextView titleText;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<Ingredient> ingredients;
    private List<Meal> meals;
    private IngredientsAdapter ingredientsAdapter;
    String ingredientsJson, responseTypeName;
    int responseTypeNumber;
    private DetailsSearchPresnter detailsSearchPresnter;
    private MealsAdapter mealsAdapter;
    private CategoriesAdapter categoriesAdapter;
    private GridLayoutManager gridLayoutManager;

    public IngredientSearchFragment() {
        // Required empty public constructor
    }


    public static IngredientSearchFragment newInstance(String param1, String param2) {
        IngredientSearchFragment fragment = new IngredientSearchFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ingredientsJson = IngredientSearchFragmentArgs.fromBundle(getArguments()).getIngradientsList();
            responseTypeNumber = IngredientSearchFragmentArgs.fromBundle(getArguments()).getResponseTypeNumber();
            responseTypeName = IngredientSearchFragmentArgs.fromBundle(getArguments()).getResponseTypeName();
            Gson gson = new Gson();
            Type type = new TypeToken<List<Ingredient>>() {
            }.getType();
            ingredients = gson.fromJson(ingredientsJson, type);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredient_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backImage = view.findViewById(R.id.btnBackImage);
        backImage.setOnClickListener(view1 -> Navigation.findNavController(view).navigateUp());
        titleText = view.findViewById(R.id.titleText);
        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.search_results_recyclerview);
        recyclerView.setHasFixedSize(true);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_scale_in);
        recyclerView.setLayoutAnimation(animation);
        detailsSearchPresnter=new DetailsSearchPresnterImpl(this, MealRemoteDataSourceImpl.getInstance());

        IngradientsRecyclerViewInit(view);

        switch (responseTypeNumber) {
            case 1:
                titleText.setText(responseTypeName);
                categoriesByNameRecyclerViewInit();
                break;
            case 2:
                titleText.setText(responseTypeName);
                ingradietsByNameRecyclerViewInit();
                break;
            case 3:
                titleText.setText(responseTypeName);
                countriesByNameRecyclerViewInit();
                break;
            default:
                break;
        }

        searchData();

    }
    private void countriesByNameRecyclerViewInit() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);

        detailsSearchPresnter.fetchCountriesMeals(responseTypeName);
        mealsAdapter = new MealsAdapter(getContext(), new ArrayList<>(),mealId -> {


            IngredientSearchFragmentDirections.ActionIngredientSearchFragmentToMealDetailsFragment action=
                    IngredientSearchFragmentDirections.actionIngredientSearchFragmentToMealDetailsFragment(mealId,false);
            NavHostFragment.findNavController(IngredientSearchFragment.this).navigate(action);

        },R.layout.meal_item);

        recyclerView.setAdapter(mealsAdapter);

    }
    private void categoriesByNameRecyclerViewInit() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        detailsSearchPresnter.fetchCategoryMeals(responseTypeName);

        mealsAdapter = new MealsAdapter(getContext(), new ArrayList<>(),mealId -> {


            IngredientSearchFragmentDirections.ActionIngredientSearchFragmentToMealDetailsFragment action=
                    IngredientSearchFragmentDirections.actionIngredientSearchFragmentToMealDetailsFragment(mealId,false);
            NavHostFragment.findNavController(IngredientSearchFragment.this).navigate(action);

        },R.layout.meal_item);
        recyclerView.setAdapter(mealsAdapter);

    }

    private void ingradietsByNameRecyclerViewInit() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        detailsSearchPresnter.fetchIngredientsMeals(responseTypeName);
        mealsAdapter = new MealsAdapter(getContext(), new ArrayList<>(),mealId -> {

            IngredientSearchFragmentDirections.ActionIngredientSearchFragmentToMealDetailsFragment action=
                    IngredientSearchFragmentDirections.actionIngredientSearchFragmentToMealDetailsFragment(mealId,false);
            NavHostFragment.findNavController(IngredientSearchFragment.this).navigate(action);

        },R.layout.meal_item);
        recyclerView.setAdapter(mealsAdapter);
    }




    private void IngradientsRecyclerViewInit(@NonNull View view) {

        titleText.setText(R.string.All_Ingradients);
        gridLayoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        ingredientsAdapter = new IngredientsAdapter(getContext(), new ArrayList<>(), true, ingredientID -> {

            IngredientSearchFragmentDirections.ActionIngredientSearchFragmentSelf action=
                    IngredientSearchFragmentDirections.actionIngredientSearchFragmentSelf(null,2,ingredientID);
            NavHostFragment.findNavController(IngredientSearchFragment.this).navigate(action);

        });
        ingredientsAdapter.setIngredients(ingredients);
        recyclerView.setAdapter(ingredientsAdapter);
    }

    private void searchData() {
        Observable<String> textChangeObservable = Observable.create(emitter -> {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(newText);
                    }
                    return true;
                }
            });
        });


        textChangeObservable
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {

                    updateSearchResults(query);
                }, Throwable::printStackTrace);
    }

    public void updateSearchResults(String query) {

        switch (responseTypeNumber)
        {
            case 0:
                List<Ingredient> filteredNames = ingredients.stream()
                    .filter(item -> item.getStrIngredient().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
                ingredientsAdapter.setIngredients(filteredNames);
                ingredientsAdapter.notifyDataSetChanged();
                break;
            default:
                List<Meal> filteredName = meals.stream()
                        .filter(item -> item.getStrMeal().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());

                mealsAdapter.setMeals(filteredName);
                mealsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void showMeals(List<Meal> meals) {

            this.meals=meals;
            mealsAdapter.setMeals(meals);
            mealsAdapter.notifyDataSetChanged();


    }

    @Override
    public void showError(String message) {

    }
}
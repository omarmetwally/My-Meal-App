package com.omarInc.mymeal.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.google.android.material.search.SearchBar;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.network.MealRemoteDataSourceImpl;
import com.omarInc.mymeal.model.MealsAdapter;
import com.omarInc.mymeal.search.presenter.SearchPresenter;
import com.omarInc.mymeal.search.presenter.SearchPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;


public class SearchFragment extends Fragment implements SearchingView {


    SearchView searchingView;
    RecyclerView searchResultsRecyclerView;
    MealsAdapter adapter;
    SearchPresenter searchPresenter;

    private final PublishSubject<String> searchSubject = PublishSubject.create();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SearchFragment() {
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


         searchingView = view.findViewById(R.id.search_view);
         searchResultsRecyclerView = view.findViewById(R.id.search_results_recyclerview);

        searchPresenter = new SearchPresenterImpl(this, MealRemoteDataSourceImpl.getInstance(getContext()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        searchResultsRecyclerView.setLayoutManager(gridLayoutManager);

        adapter = new MealsAdapter(getContext(), new ArrayList<>(),mealId -> {

            SearchFragmentDirections.ActionSearchFragmentToMealDetailsFragment action=
                    SearchFragmentDirections.actionSearchFragmentToMealDetailsFragment(mealId,false);
            NavHostFragment.findNavController(SearchFragment.this).navigate(action);
        },R.layout.meal_item);

        searchResultsRecyclerView.setAdapter(adapter);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_scale_in);
        searchResultsRecyclerView.setLayoutAnimation(animation);

      searchPresenter.performSearch(searchSubject.hide());


        searchingView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchSubject.onNext(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchSubject.onNext(newText);
                return false;
            }
        });
    }



    @Override
    public void showSearchResults(List<Meal> meals) {
        adapter.setMeals(meals);
        if(meals != null && !meals.isEmpty()) {
            searchResultsRecyclerView.scheduleLayoutAnimation();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        searchSubject.onNext("");

    }

    @Override
    public void showError(String message) {
//        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        adapter.setMeals(null);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (searchPresenter != null) {
            searchPresenter.clearSubscriptions();
        }
    }
}
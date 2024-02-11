package com.omarInc.mymeal.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import com.omarInc.mymeal.R;
import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.network.MealRemoteDataSource;
import com.omarInc.mymeal.network.MealRemoteDataSourceImpl;
import com.omarInc.mymeal.recommendedMeals.view.MealsAdapter;
import com.omarInc.mymeal.search.presenter.SearchPresenter;
import com.omarInc.mymeal.search.presenter.SearchPresenterImpl;
import com.omarInc.mymeal.search.view.SearchingView;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements SearchingView {


    SearchView searchingView;
    RecyclerView searchResultsRecyclerView;
    MealsAdapter adapter;
    SearchPresenter searchPresenter;

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

        searchPresenter = new SearchPresenterImpl(this, MealRemoteDataSourceImpl.getInstance());
// Set up the RecyclerView with a GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2); // 2 columns
        searchResultsRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MealsAdapter(getContext(), new ArrayList<>());
        searchResultsRecyclerView.setAdapter(adapter);
        searchPresenter.performSearch("");



        searchingView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPresenter.performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchPresenter.performSearch(newText);
                return false;
            }
        });
    }

    @Override
    public void showSearchResults(List<Meal> meals) {
        adapter.setMeals(meals);
    }

    @Override
    public void showError(String message) {
//        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        adapter.setMeals(null);

    }
}
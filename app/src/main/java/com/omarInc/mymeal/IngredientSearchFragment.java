package com.omarInc.mymeal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

public class IngredientSearchFragment extends Fragment {


    private ImageView backImage;
    private TextView titleText;
    private SearchView searchView;
    private RecyclerView recyclerView;

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
        titleText = view.findViewById(R.id.titleText);
        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.search_results_recyclerview);

    }
}
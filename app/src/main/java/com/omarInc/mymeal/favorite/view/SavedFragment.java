package com.omarInc.mymeal.favorite.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.favorite.presenter.FavoritePresenter;
import com.omarInc.mymeal.favorite.presenter.FavoritePresenterImpl;
import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.model.MealsAdapter;


import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment implements FavoriteView {


    private RecyclerView favRecyclerView;
    private MealsAdapter adapter;
    private FavoritePresenter presenter;

    public SavedFragment() {
    }


    public static SavedFragment newInstance(String param1, String param2) {
        SavedFragment fragment = new SavedFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FavoritePresenterImpl(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoriteInit(view);


        presenter.attachView(this);
        presenter.loadMeals();

    }

    private void favoriteInit(@NonNull View view) {
        favRecyclerView= view.findViewById(R.id.favRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2); // 2 columns
        favRecyclerView.setLayoutManager(gridLayoutManager);
        favRecyclerView.setHasFixedSize(true);
        adapter = new MealsAdapter(getContext(), new ArrayList<>(), mealId -> {

          SavedFragmentDirections.ActionSavedFragmentToMealDetailsFragment action=
                  SavedFragmentDirections.actionSavedFragmentToMealDetailsFragment(mealId,true);
          NavHostFragment.findNavController(SavedFragment.this).navigate(action);
      },R.layout.meal_item);
        favRecyclerView.setAdapter(adapter);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_scale_in);
        favRecyclerView.setLayoutAnimation(animation);



        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int position = viewHolder.getAdapterPosition();

                Meal deletedMeal = adapter.getMealAt(position);

                adapter.removeAt(position);

                Snackbar snackbar = Snackbar.make(favRecyclerView, "Meal removed", Snackbar.LENGTH_LONG);
                snackbar.setAction("Undo", view -> {
                    adapter.insertAt(position, deletedMeal);
                });
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {

                            presenter.removeMeal(deletedMeal.getIdMeal());
                        }
                    }
                });
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
            }
        };



        new ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(favRecyclerView);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void displayMeals(List<Meal> meals) {
        getActivity().runOnUiThread(() -> adapter.setMeals(meals));
    }

    @Override
    public void displayError(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return getViewLifecycleOwner();
    }
}
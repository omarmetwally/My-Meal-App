package com.omarInc.mymeal.plan.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.CalendarView;

import com.google.android.material.snackbar.Snackbar;

import com.omarInc.mymeal.R;
import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.model.MealsAdapter;
import com.omarInc.mymeal.plan.model.EventDecorator;
import com.omarInc.mymeal.plan.presenter.PlanPresenter;
import com.omarInc.mymeal.plan.presenter.PlanPresenterImpl;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanFragment extends Fragment implements PlanView {


    private MaterialCalendarView calendarView;
    private RecyclerView mealsRecyclerView;
    private MealsAdapter mealsAdapter;
    private static final String TAG = "PlanFragment";
    private PlanPresenter presenter;
    public static PlanFragment newInstance(String param1, String param2) {
        PlanFragment fragment = new PlanFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PlanPresenterImpl(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
        calendarView = view.findViewById(R.id.calendarView);
        CalendarDay today = CalendarDay.today();
        calendarView.setCurrentDate(today);
        calendarView.setSelectedDate(today);
        presenter.fetchMealsForDate(today.getDate());
        mealsRecyclerView = view.findViewById(R.id.mealsRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_scale_in);
        mealsRecyclerView.setLayoutManager(gridLayoutManager);
        mealsRecyclerView.setLayoutAnimation(animation);
        mealsAdapter = new MealsAdapter(getContext(), new ArrayList<>(),mealId -> {


            PlanFragmentDirections.ActionPlanFragmentToMealDetailsFragment action=
                    PlanFragmentDirections.actionPlanFragmentToMealDetailsFragment(mealId,false);
            NavHostFragment.findNavController(PlanFragment.this).navigate(action);

        },R.layout.meal_item);
        mealsRecyclerView.setAdapter(mealsAdapter);



        fatchFromDB();

        calendarView.setOnDateChangedListener((widget, date, selected) ->presenter.fetchMealsForDate(date.getDate()) );






        swapToDelete();
    }

    private void fatchFromDB() {
        presenter.fetchAllScheduledDates().observe(getViewLifecycleOwner(), dates -> {
            for (Date date : dates) {
                CalendarDay day = CalendarDay.from(date.getTime());
                calendarView.addDecorator(new EventDecorator(Color.CYAN, Collections.singleton(day)));
            }
        });
    }

    private void swapToDelete() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int position = viewHolder.getAdapterPosition();

                Meal deletedMeal = mealsAdapter.getMealAt(position);

                mealsAdapter.removeAt(position);

                Snackbar snackbar = Snackbar.make(mealsRecyclerView, R.string.MealRemoved, Snackbar.LENGTH_SHORT);
                snackbar.setAction(R.string.Undo, view -> {
                    mealsAdapter.insertAt(position, deletedMeal);
                });
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                            fatchFromDB();
                            Log.i(TAG, "onDismissed: "+deletedMeal.getDateScheduled());
                            presenter.removeMeal(deletedMeal.getIdMeal(),deletedMeal.getDateScheduled());

                        }
                    }
                });
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
            }
        };


        new ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(mealsRecyclerView);
    }


    @Override
    public void displayMealsForDate(List<Meal> meals) {
        mealsAdapter.setMeals(meals);
        mealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayError(String message) {

    }

    @Override
    public LifecycleOwner getLifecycleOwner(){
        return getViewLifecycleOwner();
    }

    @Override
    public void showScheduledDates(List<Date> dates) {

    }
}
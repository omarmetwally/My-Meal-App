package com.omarInc.mymeal.plan.presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.omarInc.mymeal.db.MealRepository;
import com.omarInc.mymeal.db.MealRepositoryImpl;
import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.plan.view.PlanView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PlanPresenterImpl implements PlanPresenter {
    private PlanView view;
    private MealRepository repository;


    public PlanPresenterImpl(Context context) {
        this.repository = MealRepositoryImpl.getInstance(context);
    }

    @Override
    public void attachView(PlanView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }



    @Override
    public void fetchMealsForDate(Date date) {

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(date);
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);
        long dayStart = calendarStart.getTimeInMillis();

        Calendar calendarEnd = (Calendar) calendarStart.clone();
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);
        calendarEnd.set(Calendar.MILLISECOND, 999);
        long dayEnd = calendarEnd.getTimeInMillis();

        repository.getMealsForDay(dayStart, dayEnd).observe(view.getLifecycleOwner(), meals -> {
            view.displayMealsForDate(meals);
        });

    }


    @Override
    public void removeMeal(String mealId,long scheduledDate) {
        repository.deleteScheduledMeal(mealId, scheduledDate);
        loadMeals();
    }

    @Override
    public void loadMeals() {
        repository.getAllMeals().observe(view.getLifecycleOwner(), mealDetails -> {
            List<Meal> simplifiedMeals = mealDetails.stream()
                    .map(mealDetail -> new Meal(mealDetail.getStrMeal(), mealDetail.getStrMealThumb(), mealDetail.getIdMeal()))
                    .collect(Collectors.toList());
            view.displayMealsForDate(simplifiedMeals);
        });
    }

    public LiveData<List<Date>> fetchAllScheduledDates() {
        return repository.getAllScheduledDates();
    }


}

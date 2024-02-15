package com.omarInc.mymeal.plan.presenter;

import androidx.lifecycle.LiveData;

import com.omarInc.mymeal.plan.view.PlanView;

import java.util.Date;
import java.util.List;

public interface PlanPresenter {
    void attachView(PlanView view);
    void detachView();
    void fetchMealsForDate(Date date);
    void removeMeal(String mealId,long scheduledDate);

    void loadMeals();
    LiveData<List<Date>> fetchAllScheduledDates();

}

package com.omarInc.mymeal.plan.view;

import androidx.lifecycle.LifecycleOwner;

import com.omarInc.mymeal.model.Meal;

import java.util.Date;
import java.util.List;

public interface PlanView {
    void displayMealsForDate(List<Meal> meals);
    void displayError(String message);
    LifecycleOwner getLifecycleOwner();
   void showScheduledDates(List<Date> dates);
}

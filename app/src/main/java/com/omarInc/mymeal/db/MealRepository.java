package com.omarInc.mymeal.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.model.MealDetail;
import com.omarInc.mymeal.plan.model.ScheduledMeal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public interface MealRepository {
//    void insert(MealDetail mealDetail);
//
//    LiveData<MealDetail> getMealById(String idMeal);
//
//    void delete(MealDetail mealDetail);
//
//    void deleteById(String idMeal);
//    void toggleFavorite(MealDetail mealDetail);
//    LiveData<Boolean> isFavorite(String mealId);
//    LiveData<List<MealDetail>> getAllMeals();
//    LiveData<List<ScheduledMeal>> getAllSchedules();
//    void clearAll();
//
//    void insertAll(List<MealDetail> meals);
//
//    void insertAllScheduledMeals(List<ScheduledMeal> meals);
//
//
//
//    void scheduleMeal(MealDetail mealDetail, Date selectedDate);
//    LiveData<List<Meal>> getMealsForDay(long dayStart, long dayEnd);
//    void deleteScheduledMeal(String mealId, long scheduledDate);
//    //    LiveData<List<ScheduledMeal>> getScheduledMealsForDate(Date date);
//
//    LiveData<List<Date>> getAllScheduledDates();

    Completable insert(MealDetail mealDetail);

    Flowable<MealDetail> getMealById(String idMeal);

    Completable delete(MealDetail mealDetail);

    Completable deleteById(String idMeal);

    Completable toggleFavorite(MealDetail mealDetail);

    Single<Boolean> isFavorite(String mealId);

    Flowable<List<MealDetail>> getAllMeals();

    LiveData<List<ScheduledMeal>> getAllSchedules();

    void clearAll();

    Completable insertAll(List<MealDetail> meals);

    void insertAllScheduledMeals(List<ScheduledMeal> meals);

    void scheduleMeal(MealDetail mealDetail, Date selectedDate);

    LiveData<List<Meal>> getMealsForDay(long dayStart, long dayEnd);

    void deleteScheduledMeal(String mealId, long scheduledDate);

    LiveData<List<Date>> getAllScheduledDates();

}

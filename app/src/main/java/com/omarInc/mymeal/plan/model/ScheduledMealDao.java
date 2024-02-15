package com.omarInc.mymeal.plan.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.model.MealDetail;

import java.util.Date;
import java.util.List;

@Dao
public interface ScheduledMealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ScheduledMeal scheduledMeal);


    @Query("SELECT * FROM scheduled_meals WHERE dateScheduled >= :dayStart AND dateScheduled <= :dayEnd")
    LiveData<List<Meal>> getMealsForDay(long dayStart, long dayEnd);

    @Query("DELETE FROM scheduled_meals")
    void clearAll();

    @Query("DELETE FROM scheduled_meals WHERE idMeal = :idMeal AND dateScheduled = :dateScheduled")
    void deleteByIdAndDate(String idMeal, long dateScheduled);


    @Query("SELECT DISTINCT dateScheduled FROM scheduled_meals ORDER BY dateScheduled ASC")
    LiveData<List<Long>> getAllScheduledDates();


    @Query("SELECT * FROM scheduled_meals")
    LiveData<List<ScheduledMeal>> getAllSchedules();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ScheduledMeal> mealDetails);

}

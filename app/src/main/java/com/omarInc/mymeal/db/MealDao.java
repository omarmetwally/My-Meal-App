package com.omarInc.mymeal.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.omarInc.mymeal.model.MealDetail;

import java.util.List;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MealDetail mealDetail);

    @Query("SELECT * FROM meal_table WHERE idMeal = :idMeal")
    LiveData<MealDetail> getMealById(String idMeal);
    @Delete
    void delete(MealDetail mealDetail);

    // If you want to delete by ID:
    @Query("DELETE FROM meal_table WHERE idMeal = :idMeal")
    void deleteById(String idMeal);

    @Query("SELECT COUNT(idMeal) FROM meal_table WHERE idMeal = :mealId")
    LiveData<Integer> hasMeal(String mealId);
    @Query("SELECT COUNT(*) FROM meal_table WHERE idMeal = :idMeal")
    int hasMealSync(String idMeal);

    @Query("SELECT * FROM meal_table")
    LiveData<List<MealDetail>> getAllMeals();
    @Query("DELETE FROM meal_table")
    void clearAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MealDetail> mealDetails);


}

package com.omarInc.mymeal.db;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.omarInc.mymeal.model.MealDetail;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MealRepositoryImpl implements MealRepository {
    private MealDao mealDao;
    private static volatile MealRepository instance;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private MealRepositoryImpl(Context context) {
        DataBase database = DataBase.getInstance(context);
        mealDao = database.mealDao();
    }

    public static MealRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (MealRepositoryImpl.class) {
                if (instance == null) {
                    instance = new MealRepositoryImpl(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void insert(MealDetail mealDetail) {
        new Thread(() -> mealDao.insert(mealDetail)).start();
    }

    @Override
    public LiveData<MealDetail> getMealById(String idMeal) {
        // Since this is a synchronous operation, it should not be called on the UI thread.
        // Consider using LiveData or another async pattern.
        return mealDao.getMealById(idMeal);
    }

    @Override
    public void delete(MealDetail mealDetail) {
        new Thread(() -> mealDao.delete(mealDetail)).start();
    }

    @Override
    public void deleteById(String idMeal) {
        new Thread(() -> mealDao.deleteById(idMeal)).start();
    }

    @Override
    public void toggleFavorite(MealDetail mealDetail) {
        new Thread(() -> {
            int count = mealDao.hasMealSync(mealDetail.getIdMeal());
            if (count > 0) {
                mealDao.deleteById(mealDetail.getIdMeal());
            } else {
                mealDao.insert(mealDetail);
            }
        }).start();
    }


    @Override
    public LiveData<Boolean> isFavorite(String mealId) {

        return Transformations.map(mealDao.hasMeal(mealId), count -> count > 0);

    }

    @Override
    public LiveData<List<MealDetail>> getAllMeals() {
        return mealDao.getAllMeals();
    }

    @Override
    public void clearAll() {
        new Thread(() ->  mealDao.clearAll()).start();
    }

    @Override
    public void insertAll(List<MealDetail> meals) {
        executor.execute(() -> mealDao.insertAll(meals));
    }
}

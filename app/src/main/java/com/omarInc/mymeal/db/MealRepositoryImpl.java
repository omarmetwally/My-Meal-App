package com.omarInc.mymeal.db;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.model.MealDetail;
import com.omarInc.mymeal.plan.model.ScheduledMeal;
import com.omarInc.mymeal.plan.model.ScheduledMealDao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MealRepositoryImpl implements MealRepository {
    private MealDao mealDao;

    private static volatile MealRepository instance;
    private ScheduledMealDao scheduledMealDao;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private MealRepositoryImpl(Context context) {
        DataBase database = DataBase.getInstance(context);
        mealDao = database.mealDao();
        scheduledMealDao = database.scheduledMealDao();
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
    public void deleteScheduledMeal(String mealId, long scheduledDate) {
        new Thread(() -> scheduledMealDao.deleteByIdAndDate(mealId, scheduledDate)).start();
    }

    @Override
    public LiveData<List<Date>> getAllScheduledDates() {
        return Transformations.map(scheduledMealDao.getAllScheduledDates(), timestamps -> {
            List<Date> dates = new ArrayList<>();
            for (Long timestamp : timestamps) {
                dates.add(new Date(timestamp));
            }
            return dates;
        });
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
    public LiveData<List<ScheduledMeal>> getAllSchedules() {
        return scheduledMealDao.getAllSchedules();
    }

    @Override
    public void clearAll() {
        new Thread(() ->  {
            mealDao.clearAll();
            scheduledMealDao.clearAll();

        }).start();
    }

    @Override
    public void insertAll(List<MealDetail> meals) {
        executor.execute(() -> mealDao.insertAll(meals));
    }

    @Override
    public void insertAllScheduledMeals(List<ScheduledMeal> meals) {
        executor.execute(() -> scheduledMealDao.insertAll(meals));
    }

    @Override
    public void scheduleMeal(MealDetail mealDetail, Date selectedDate) {
        ScheduledMeal scheduledMeal = new ScheduledMeal();
        scheduledMeal.setIdMeal( mealDetail.getIdMeal());
        scheduledMeal.setStrMeal( mealDetail.getStrMeal());
        scheduledMeal.setStrMealThumb(mealDetail.getStrMealThumb());
        scheduledMeal.setDateScheduled( selectedDate.getTime());
         executor.execute(() -> scheduledMealDao.insert(scheduledMeal));
    }

    @Override
    public LiveData<List<Meal>> getMealsForDay(long dayStart, long dayEnd) {
        return scheduledMealDao.getMealsForDay(dayStart, dayEnd);
    }



//    @Override
//    public LiveData<List<ScheduledMeal>> getScheduledMealsForDate(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        Date startOfDay = calendar.getTime();
//
//        calendar.add(Calendar.DAY_OF_MONTH, 1);
//        Date endOfDay = calendar.getTime();
//
//        return scheduledMealDao.getScheduledMealsForDate(startOfDay, endOfDay);
//    }
}

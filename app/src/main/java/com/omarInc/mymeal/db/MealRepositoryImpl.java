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

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


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
    public Completable insert(MealDetail mealDetail) {
        return mealDao.insert(mealDetail)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<MealDetail> getMealById(String idMeal) {
        return mealDao.getMealById(idMeal)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable delete(MealDetail mealDetail) {
        return mealDao.delete(mealDetail)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteById(String idMeal) {
        return mealDao.deleteById(idMeal)
                .subscribeOn(Schedulers.io());    }
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
    public Completable toggleFavorite(MealDetail mealDetail) {
        return isFavorite(mealDetail.getIdMeal())
                .flatMapCompletable(isFavorite -> {
                    if (isFavorite) {
                        return mealDao.deleteById(mealDetail.getIdMeal());
                    } else {
                        return mealDao.insert(mealDetail);
                    }
                })
                .subscribeOn(Schedulers.io());
    }


    @Override
    public Single<Boolean> isFavorite(String mealId) {

        return mealDao.hasMeal(mealId)
                .map(count -> count > 0)
                .subscribeOn(Schedulers.io());

    }

    @Override
    public Flowable<List<MealDetail>>getAllMeals() {
        return mealDao.getAllMeals()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public LiveData<List<ScheduledMeal>> getAllSchedules() {
        return scheduledMealDao.getAllSchedules();
    }

    @Override
    public void  clearAll() {
        new Thread(() ->  {
            mealDao.clearAll();
            scheduledMealDao.clearAll();

        }).start();
    }

    @Override
    public Completable insertAll(List<MealDetail> meals) {
        return mealDao.insertAll(meals)
                .subscribeOn(Schedulers.io());
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

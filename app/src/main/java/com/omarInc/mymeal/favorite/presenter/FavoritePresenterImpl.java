package com.omarInc.mymeal.favorite.presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.omarInc.mymeal.db.MealRepository;
import com.omarInc.mymeal.db.MealRepositoryImpl;
import com.omarInc.mymeal.favorite.view.FavoriteView;
import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.model.MealDetail;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenterImpl implements  FavoritePresenter{
    private FavoriteView view;
    private MealRepository mealRepository;
    private CompositeDisposable disposables = new CompositeDisposable();

    public FavoritePresenterImpl(Context context) {
        this.mealRepository = MealRepositoryImpl.getInstance(context);
    }
    @Override
    public void loadMeals() {

//        mealRepository.getAllMeals().observe(view.getLifecycleOwner(), mealDetails -> {
//            List<Meal> simplifiedMeals = mealDetails.stream()
//                    .map(mealDetail -> new Meal(mealDetail.getStrMeal(), mealDetail.getStrMealThumb(), mealDetail.getIdMeal()))
//                    .collect(Collectors.toList());
//            view.displayMeals(simplifiedMeals);
//        });
        disposables.add(mealRepository.getAllMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealDetails -> {
                            List<Meal> simplifiedMeals = mealDetails.stream()
                                    .map(mealDetail -> new Meal(mealDetail.getStrMeal(), mealDetail.getStrMealThumb(), mealDetail.getIdMeal()))
                                    .collect(Collectors.toList());
                            view.displayMeals(simplifiedMeals);
                        },
                        throwable -> {}
                ));
    }

    @Override
    public void attachView(FavoriteView view) {
        this.view = view;

    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void removeMeal(String mealId) {
//        mealRepository.deleteById(mealId);
//        loadMeals();
        disposables.add(mealRepository.deleteById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> loadMeals(),
                        throwable -> {}
                ));
    }


}

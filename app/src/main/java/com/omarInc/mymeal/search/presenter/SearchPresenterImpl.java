package com.omarInc.mymeal.search.presenter;

import com.omarInc.mymeal.model.MealsResponse;
import com.omarInc.mymeal.network.MealRemoteDataSource;
import com.omarInc.mymeal.network.NetworkCallBack;
import com.omarInc.mymeal.search.view.SearchingView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {
    private SearchingView searchingView;
    private MealRemoteDataSource mealRemoteDataSource;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SearchPresenterImpl(SearchingView view, MealRemoteDataSource dataSource) {
        this.searchingView = view;
        this.mealRemoteDataSource = dataSource;
    }

    @Override
    public void performSearch(Observable<String> searchQueryObservable) {
        compositeDisposable.clear();

        Disposable disposable = searchQueryObservable
                .debounce(1, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .flatMap(query -> mealRemoteDataSource.searchMealsByName(query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsResponse -> searchingView.showSearchResults(mealsResponse.getMeals()),
                        throwable -> searchingView.showError(throwable.getMessage())
                );

        compositeDisposable.add(disposable);

    }
@Override
    public void clearSubscriptions() {
        compositeDisposable.clear();
    }
}
package com.omarInc.mymeal.search.presenter;



public interface SearchPresenter {
//    void performSearch(Observable<String> searchQueryObservable);
     void performSearch(io.reactivex.rxjava3.core.Observable<String> searchQueryObservable);
     void clearSubscriptions();
}

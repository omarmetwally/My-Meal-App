package com.omarInc.mymeal.search.view;

import com.omarInc.mymeal.model.Meal;

import java.util.List;

public interface SearchingView {
    void showSearchResults(List<Meal> meals);
    void showError(String message);
}
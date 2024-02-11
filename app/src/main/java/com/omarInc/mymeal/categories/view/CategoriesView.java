package com.omarInc.mymeal.categories.view;

import com.omarInc.mymeal.model.Category;

import java.util.List;

public interface CategoriesView {
    void showCategories(List<Category> categories);
    void showError(String error);
}

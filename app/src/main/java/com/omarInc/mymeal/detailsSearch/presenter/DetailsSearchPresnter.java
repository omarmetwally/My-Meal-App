package com.omarInc.mymeal.detailsSearch.presenter;

public interface DetailsSearchPresnter {
    void fetchCategoryMeals(String categoryName);
    void fetchIngredientsMeals(String ingredientName);
    void fetchCountriesMeals(String countryName);
}

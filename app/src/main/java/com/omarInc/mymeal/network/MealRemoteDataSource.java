package com.omarInc.mymeal.network;

import com.omarInc.mymeal.area.model.AreasResponse;
import com.omarInc.mymeal.model.CategoryResponse;
import com.omarInc.mymeal.model.IngredientsResponse;
import com.omarInc.mymeal.model.MealDetailResponse;
import com.omarInc.mymeal.model.MealsResponse;

public interface MealRemoteDataSource {

    void getCategories(NetworkCallBack<CategoryResponse> networkCallBack);

    void getMealsByCategory(String category, NetworkCallBack<MealsResponse> networkCallBack);

    void getMealById(String mealId, NetworkCallBack<MealDetailResponse> networkCallBack);

    void getLatestMeals(NetworkCallBack<MealsResponse> networkCallBack);

    void getRandomMeal(NetworkCallBack<MealDetailResponse> networkCallBack);

    void getMealsByArea(String area, NetworkCallBack<MealsResponse> networkCallBack);

    void getMealsByIngredient(String ingredient, NetworkCallBack<MealsResponse> networkCallBack);

    void searchMealsByName(String mealName, NetworkCallBack<MealsResponse> networkCallBack);

    void getIngredients(NetworkCallBack<IngredientsResponse> networkCallBack);
    void getAreas(NetworkCallBack<AreasResponse> networkCallBack);

    // lesa fe 7agat tanya isA mansash to Add additional method signatures as in the API's documentation
}

package com.omarInc.mymeal.network;

import com.omarInc.mymeal.area.model.AreasResponse;
import com.omarInc.mymeal.model.CategoryResponse;
import com.omarInc.mymeal.model.IngredientsResponse;
import com.omarInc.mymeal.model.MealDetailResponse;
import com.omarInc.mymeal.model.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("api/json/v1/1/categories.php")
    Call<CategoryResponse> getCategories();

    @GET("api/json/v1/1/filter.php")
    Call<MealsResponse> getMealsByCategory(@Query("c") String category);

    @GET("api/json/v1/1/lookup.php")
    Call<MealDetailResponse> getMealById(@Query("i") String mealId);

    @GET("api/json/v1/1/latest.php")
    Call<MealsResponse> getLatestMeals();

    @GET("api/json/v1/1/random.php")
    Call<MealDetailResponse> getRandomMeal();

    @GET("api/json/v1/1/filter.php")
    Call<MealsResponse> getMealsByArea(@Query("a") String area);

    @GET("api/json/v1/1/filter.php")
    Call<MealsResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("api/json/v1/1/search.php")
    Call<MealsResponse> searchMealsByName(@Query("s") String mealName);
    @GET("api/json/v1/1/list.php?i=list")
    Call<IngredientsResponse> getIngredients();

    @GET("api/json/v1/1/list.php?a=list")
    Call<AreasResponse> getAreas();



}

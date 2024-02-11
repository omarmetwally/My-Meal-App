package com.omarInc.mymeal.network;

import android.util.Log;
import android.widget.Toast;

import com.omarInc.mymeal.area.model.AreasResponse;
import com.omarInc.mymeal.model.CategoryResponse;
import com.omarInc.mymeal.model.IngredientsResponse;
import com.omarInc.mymeal.model.MealDetailResponse;
import com.omarInc.mymeal.model.MealsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSourceImpl implements MealRemoteDataSource {
    private static MealRemoteDataSourceImpl instance = null;
    private MealService mealService;
    private static final String TAG = "MealRemoteDataSourceImp";

    private MealRemoteDataSourceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }

    public static synchronized MealRemoteDataSourceImpl getInstance() {
        if (instance == null) {
            instance = new MealRemoteDataSourceImpl();
        }
        return instance;
    }

    @Override
    public void getCategories(NetworkCallBack<CategoryResponse> networkCallBack) {
        Call<CategoryResponse> call = mealService.getCategories();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallBack.onSuccessResult(response.body());
//                    Log.i(TAG, "getCategories Response: "+response.body().getCategories().toString());

                } else {
                    networkCallBack.onFailureResult("Response unsuccessful");
//                    Log.i(TAG, "getCategories Failure: "+response.body());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                networkCallBack.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getIngredients(NetworkCallBack<IngredientsResponse> networkCallBack) {
        Call<IngredientsResponse> call = mealService.getIngredients();
        call.enqueue(new Callback<IngredientsResponse>() {
            @Override
            public void onResponse(Call<IngredientsResponse> call, Response<IngredientsResponse> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body());
//                    Log.i(TAG, "getIngredients Response: "+response.body().getIngredients().toString());
                } else {
                    networkCallBack.onFailureResult("Error fetching ingredients");
//                    Log.i(TAG, "getIngredients Failure: "+response.body());

                }
            }

            @Override
            public void onFailure(Call<IngredientsResponse> call, Throwable t) {
                networkCallBack.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getAreas(NetworkCallBack<AreasResponse> networkCallBack) {
        Call<AreasResponse> call = mealService.getAreas();
        call.enqueue(new Callback<AreasResponse>() {
            @Override
            public void onResponse(Call<AreasResponse> call, Response<AreasResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallBack.onSuccessResult(response.body());
//                    Log.i(TAG, "getAreas Response: Suc "+response.body());

                } else {
                    networkCallBack.onFailureResult("Failed to fetch areas: " + response.message());
//                    Log.i(TAG, "getAreas Response: Fal "+response.body());

                }
            }

            @Override
            public void onFailure(Call<AreasResponse> call, Throwable t) {
                networkCallBack.onFailureResult("Network error: " + t.getMessage());
            }
        });
    }
    @Override
    public void getMealsByCategory(String category, NetworkCallBack<MealsResponse> networkCallBack) {
        Call<MealsResponse> call = mealService.getMealsByCategory(category);
        enqueueCall(call, networkCallBack);
    }

    @Override
    public void getMealById(String mealId, NetworkCallBack<MealDetailResponse> networkCallBack) {
        Call<MealDetailResponse> call = mealService.getMealById(mealId);
        enqueueCall(call, networkCallBack);
    }

    @Override
    public void getLatestMeals(NetworkCallBack<MealsResponse> networkCallBack) {
        Call<MealsResponse> call = mealService.getLatestMeals();
        enqueueCall(call, networkCallBack);
//        Log.i(TAG, "onResponse: "+"sssssssssssssssssssssssssssssss");
    }

    @Override
    public void getRandomMeal(NetworkCallBack<MealDetailResponse> networkCallBack) {
        Call<MealDetailResponse> call = mealService.getRandomMeal();
        enqueueCall(call, networkCallBack);
    }

    @Override
    public void getMealsByArea(String area, NetworkCallBack<MealsResponse> networkCallBack) {
        Call<MealsResponse> call = mealService.getMealsByArea(area);
        enqueueCall(call, networkCallBack);

    }

    @Override
    public void getMealsByIngredient(String ingredient, NetworkCallBack<MealsResponse> networkCallBack) {
        Call<MealsResponse> call = mealService.getMealsByIngredient(ingredient);
        enqueueCall(call, networkCallBack);
    }

    @Override
    public void searchMealsByName(String mealName, NetworkCallBack<MealsResponse> networkCallBack) {
        Call<MealsResponse> call = mealService.searchMealsByName(mealName);
        enqueueCall(call, networkCallBack);
    }

    // Generic method to enqueue the Retrofit call and handle the callback
    private <T> void enqueueCall(Call<T> call, NetworkCallBack<T> networkCallBack) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallBack.onSuccessResult(response.body());
                    Log.i(TAG, "sussss: "+response.body());
                } else {
                    networkCallBack.onFailureResult("Response unsuccessful");
                    Log.i(TAG, "failllll: "+response.body());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                networkCallBack.onFailureResult(t.getMessage());
                Log.i(TAG, "onResponse: "+t.getMessage());
            }
        });
    }

    // Implement additional methods for other endpoints as needed
}

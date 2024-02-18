package com.omarInc.mymeal.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.omarInc.mymeal.area.model.AreasResponse;
import com.omarInc.mymeal.model.CategoryResponse;
import com.omarInc.mymeal.model.IngredientsResponse;
import com.omarInc.mymeal.model.MealDetailResponse;
import com.omarInc.mymeal.model.MealsResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSourceImpl implements MealRemoteDataSource {
    private static MealRemoteDataSourceImpl instance = null;
    private MealService mealService;
    private static final String TAG = "MealRemoteDataSourceImp";

    private MealRemoteDataSourceImpl(Context context) {
        int cacheSize = 100 * 1024 * 1024;
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }

    public static synchronized MealRemoteDataSourceImpl getInstance(Context context) {
        if (instance == null) {
            instance = new MealRemoteDataSourceImpl(context);
        }
        return instance;
    }
//
//    @Override
//    public void getCategories(NetworkCallBack<CategoryResponse> networkCallBack) {
//        Call<CategoryResponse> call = mealService.getCategories();
//        call.enqueue(new Callback<CategoryResponse>() {
//            @Override
//            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    networkCallBack.onSuccessResult(response.body());
////                    Log.i(TAG, "getCategories Response: "+response.body().getCategories().toString());
//
//                } else {
//                    networkCallBack.onFailureResult("Response unsuccessful");
////                    Log.i(TAG, "getCategories Failure: "+response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CategoryResponse> call, Throwable t) {
//                networkCallBack.onFailureResult(t.getMessage());
//            }
//        });
//    }
//
//    @Override
//    public void getIngredients(NetworkCallBack<IngredientsResponse> networkCallBack) {
//        Call<IngredientsResponse> call = mealService.getIngredients();
//        call.enqueue(new Callback<IngredientsResponse>() {
//            @Override
//            public void onResponse(Call<IngredientsResponse> call, Response<IngredientsResponse> response) {
//                if (response.isSuccessful()) {
//                    networkCallBack.onSuccessResult(response.body());
////                    Log.i(TAG, "getIngredients Response: "+response.body().getIngredients().toString());
//                } else {
//                    networkCallBack.onFailureResult("Error fetching ingredients");
////                    Log.i(TAG, "getIngredients Failure: "+response.body());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<IngredientsResponse> call, Throwable t) {
//                networkCallBack.onFailureResult(t.getMessage());
//            }
//        });
//    }
//
//    @Override
//    public void getAreas(NetworkCallBack<AreasResponse> networkCallBack) {
//        Call<AreasResponse> call = mealService.getAreas();
//        call.enqueue(new Callback<AreasResponse>() {
//            @Override
//            public void onResponse(Call<AreasResponse> call, Response<AreasResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    networkCallBack.onSuccessResult(response.body());
////                    Log.i(TAG, "getAreas Response: Suc "+response.body());
//
//                } else {
//                    networkCallBack.onFailureResult("Failed to fetch areas: " + response.message());
////                    Log.i(TAG, "getAreas Response: Fal "+response.body());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AreasResponse> call, Throwable t) {
//                networkCallBack.onFailureResult("Network error: " + t.getMessage());
//            }
//        });
//    }
//    @Override
//    public void getMealsByCategory(String category, NetworkCallBack<MealsResponse> networkCallBack) {
//        Call<MealsResponse> call = mealService.getMealsByCategory(category);
//        enqueueCall(call, networkCallBack);
//    }
//
//    @Override
//    public void getMealById(String mealId, NetworkCallBack<MealDetailResponse> networkCallBack) {
//        Call<MealDetailResponse> call = mealService.getMealById(mealId);
//        enqueueCall(call, networkCallBack);
//    }
//
//    @Override
//    public void getLatestMeals(NetworkCallBack<MealsResponse> networkCallBack) {
//        Call<MealsResponse> call = mealService.getLatestMeals();
//        enqueueCall(call, networkCallBack);
////        Log.i(TAG, "onResponse: "+"sssssssssssssssssssssssssssssss");
//    }
//
//    @Override
//    public void getRandomMeal(NetworkCallBack<MealDetailResponse> networkCallBack) {
//        Call<MealDetailResponse> call = mealService.getRandomMeal();
//        enqueueCall(call, networkCallBack);
//    }
//
//    @Override
//    public void getMealsByArea(String area, NetworkCallBack<MealsResponse> networkCallBack) {
//        Call<MealsResponse> call = mealService.getMealsByArea(area);
//        enqueueCall(call, networkCallBack);
//
//    }
//
//    @Override
//    public void getMealsByIngredient(String ingredient, NetworkCallBack<MealsResponse> networkCallBack) {
//        Call<MealsResponse> call = mealService.getMealsByIngredient(ingredient);
//        enqueueCall(call, networkCallBack);
//    }
//
//    @Override
//    public void searchMealsByName(String mealName, NetworkCallBack<MealsResponse> networkCallBack) {
//        Call<MealsResponse> call = mealService.searchMealsByName(mealName);
//        enqueueCall(call, networkCallBack);
//    }
//
//    private <T> void enqueueCall(Call<T> call, NetworkCallBack<T> networkCallBack) {
//        call.enqueue(new Callback<T>() {
//            @Override
//            public void onResponse(Call<T> call, Response<T> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    networkCallBack.onSuccessResult(response.body());
//                    Log.i(TAG, "sussss: "+response.body());
//                } else {
//                    networkCallBack.onFailureResult("Response unsuccessful");
//                    Log.i(TAG, "failllll: "+response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<T> call, Throwable t) {
//                networkCallBack.onFailureResult(t.getMessage());
//                Log.i(TAG, "onResponse: "+t.getMessage());
//            }
//        });
//    }

    private <T> void makeRequest(Observable<T> observable, NetworkCallBack<T> callback) {
        Disposable disposable = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        callback::onSuccessResult,
                        throwable -> callback.onFailureResult(throwable.getMessage())
                );
    }

    @Override
    public void getCategories(NetworkCallBack<CategoryResponse> networkCallBack) {
        makeRequest(mealService.getCategories(), networkCallBack);
    }

    @Override
    public void getIngredients(NetworkCallBack<IngredientsResponse> networkCallBack) {
        makeRequest(mealService.getIngredients(), networkCallBack);
    }

    @Override
    public void getAreas(NetworkCallBack<AreasResponse> networkCallBack) {
        makeRequest(mealService.getAreas(), networkCallBack);
    }

    @Override
    public void getMealsByCategory(String category, NetworkCallBack<MealsResponse> networkCallBack) {
        makeRequest(mealService.getMealsByCategory(category), networkCallBack);
    }

    @Override
    public void getMealById(String mealId, NetworkCallBack<MealDetailResponse> networkCallBack) {
        makeRequest(mealService.getMealById(mealId), networkCallBack);
    }

    @Override
    public void getLatestMeals(NetworkCallBack<MealsResponse> networkCallBack) {
        makeRequest(mealService.getLatestMeals(), networkCallBack);
    }

    @Override
    public void getRandomMeal(NetworkCallBack<MealDetailResponse> networkCallBack) {
        makeRequest(mealService.getRandomMeal(), networkCallBack);
    }

    @Override
    public void getMealsByArea(String area, NetworkCallBack<MealsResponse> networkCallBack) {
        makeRequest(mealService.getMealsByArea(area), networkCallBack);
    }

    @Override
    public void getMealsByIngredient(String ingredient, NetworkCallBack<MealsResponse> networkCallBack) {
        makeRequest(mealService.getMealsByIngredient(ingredient), networkCallBack);
    }

    @Override
    public Observable<MealsResponse> searchMealsByName(String query) {
        return mealService.searchMealsByName(query);
    }

}

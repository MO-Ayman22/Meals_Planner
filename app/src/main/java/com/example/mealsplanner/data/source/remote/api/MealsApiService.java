package com.example.mealsplanner.data.source.remote.api;

import com.example.mealsplanner.data.model.wrapper.AreaListResponse;
import com.example.mealsplanner.data.model.wrapper.CategoriesResponse;
import com.example.mealsplanner.data.model.wrapper.MealPreviewListResponse;
import com.example.mealsplanner.data.model.wrapper.MealsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsApiService {

    @GET("random.php")
    Single<MealsResponse> getRandomMeal();

    @GET("lookup.php")
    Single<MealsResponse> getMealById(@Query("i") String id);

    @GET("categories.php")
    Single<CategoriesResponse> getCategories();

    @GET("list.php?a=list")
    Single<AreaListResponse> getAreas();

    @GET("search.php")
    Single<MealsResponse> searchMealsByName(@Query("s") String query);

    @GET("search.php")
    Single<MealsResponse> searchMealsByFirstLetter(@Query("f") char letter);


    @GET("filter.php")
    Single<MealPreviewListResponse> filterByArea(@Query("a") String area);

    @GET("filter.php")
    Single<MealPreviewListResponse> filterByCategory(@Query("c") String category);

}

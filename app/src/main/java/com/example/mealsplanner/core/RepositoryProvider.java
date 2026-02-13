package com.example.mealsplanner.core;

import android.content.Context;

import com.example.mealsplanner.data.repository.AreaRepository;
import com.example.mealsplanner.data.repository.CategoryRepository;
import com.example.mealsplanner.data.repository.FavoriteRepository;
import com.example.mealsplanner.data.repository.MealRepository;
import com.example.mealsplanner.data.repository.PlannedMealsRepository;
import com.example.mealsplanner.data.repository.UserRepository;
import com.example.mealsplanner.data.source.local.db.AppDatabase;
import com.example.mealsplanner.data.source.local.favorite.FavoriteLocalDataSourceImpl;
import com.example.mealsplanner.data.source.local.mealsource.MealsLocalDataSourceImpl;
import com.example.mealsplanner.data.source.local.planner.PlannedMealLocalDataSourceImpl;
import com.example.mealsplanner.data.source.local.usersource.UserLocalDataSourceImpl;
import com.example.mealsplanner.data.source.remote.api.RetrofitClient;
import com.example.mealsplanner.data.source.remote.favorite.FavoriteFirebaseDataSource;
import com.example.mealsplanner.data.source.remote.favorite.FavoriteRemoteDataSource;
import com.example.mealsplanner.data.source.remote.mealsource.MealsRemoteDataSourceImpl;
import com.example.mealsplanner.data.source.remote.planner.PlannedMealRemoteDataSourceImpl;
import com.example.mealsplanner.data.source.remote.usersource.UserRemoteDataSourceImpl;

public final class RepositoryProvider {

    private RepositoryProvider() {
    }

    // ------------------- User -------------------
    public static UserRepository provideUserRepository(Context context) {
        return new UserRepository(
                new UserRemoteDataSourceImpl(),
                new UserLocalDataSourceImpl(AppDatabase.getInstance(context).getUserDAO())
        );
    }

    // ------------------- Meals -------------------
    public static MealsLocalDataSourceImpl provideMealsLocal(Context context) {
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());
        return new MealsLocalDataSourceImpl(
                db.getAreaDAO(),
                db.getCategoryDAO(),
                db.getPlannedMealDAO(),
                db.getFavoriteMealDAO()
        );
    }

    public static MealsRemoteDataSourceImpl provideMealsRemote() {
        return new MealsRemoteDataSourceImpl(RetrofitClient.getInstance().getApiService());
    }

    public static MealRepository provideMealRepo(Context context) {
        return new MealRepository(provideMealsRemote());
    }

    public static CategoryRepository provideCategoryRepo(Context context) {
        return new CategoryRepository(provideMealsLocal(context), provideMealsRemote());
    }

    public static AreaRepository provideAreaRepo(Context context) {
        return new AreaRepository(provideMealsLocal(context), provideMealsRemote());
    }

    // ------------------- Favorites -------------------
    public static FavoriteRepository provideFavoriteRepo(Context context) {
        FavoriteLocalDataSourceImpl local = new FavoriteLocalDataSourceImpl(
                AppDatabase.getInstance(context).getFavoriteMealDAO(),
                SessionManager.getInstance(context).getUserId()
        );
        FavoriteRemoteDataSource remote = new FavoriteFirebaseDataSource();
        return new FavoriteRepository(local, remote, SessionManager.getInstance(context).getUserId());
    }

    // ------------------- Planned Meals -------------------
    public static PlannedMealsRepository providePlannedMealsRepo(Context context) {
        return new PlannedMealsRepository(
                new PlannedMealLocalDataSourceImpl(AppDatabase.getInstance(context).getPlannedMealDAO()),
                new PlannedMealRemoteDataSourceImpl()
        );
    }
}

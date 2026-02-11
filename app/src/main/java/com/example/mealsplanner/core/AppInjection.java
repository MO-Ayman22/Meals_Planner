package com.example.mealsplanner.core;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.mealsplanner.data.repository.AreaRepository;
import com.example.mealsplanner.data.repository.CategoryRepository;
import com.example.mealsplanner.data.repository.MealRepository;
import com.example.mealsplanner.data.source.local.db.AppDatabase;
import com.example.mealsplanner.data.source.local.mealsource.MealsLocalDataSourceImpl;
import com.example.mealsplanner.data.source.remote.api.RetrofitClient;
import com.example.mealsplanner.data.source.remote.mealsource.MealsRemoteDataSourceImpl;
import com.example.mealsplanner.presentation.main.areameals.contract.AreaMealsContract;
import com.example.mealsplanner.presentation.main.areameals.presenter.AreaMealsPresenter;
import com.example.mealsplanner.presentation.main.categories.contract.CategoriesContract;
import com.example.mealsplanner.presentation.main.categories.presenter.CategoriesPresenter;
import com.example.mealsplanner.presentation.main.categorymeals.contract.CategoryMealsContract;
import com.example.mealsplanner.presentation.main.categorymeals.presenter.CategoryMealsPresenter;
import com.example.mealsplanner.presentation.main.home.contract.HomeContract;
import com.example.mealsplanner.presentation.main.home.presenter.HomePresenter;
import com.example.mealsplanner.presentation.main.mealdetails.contract.MealDetailsContract;
import com.example.mealsplanner.presentation.main.mealdetails.presenter.MealDetailsPresenter;

public final class AppInjection {


    private AppInjection() {
    }

    // Data Sources
    @NonNull
    private static MealsLocalDataSourceImpl provideLocalDataSource(Context context) {
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());

        return new MealsLocalDataSourceImpl(
                db.getAreaDAO(),
                db.getCategoryDAO(),
                db.getPlannedMealDAO(),
                db.getFavoriteMealDAO()
        );
    }

    @NonNull
    private static MealsRemoteDataSourceImpl provideRemoteDataSource() {
        return new MealsRemoteDataSourceImpl(
                RetrofitClient.getInstance().getApiService()
        );
    }

    // Repositories

    @NonNull
    private static CategoryRepository provideCategoryRepository(
            MealsLocalDataSourceImpl local,
            MealsRemoteDataSourceImpl remote
    ) {
        return new CategoryRepository(local, remote);
    }

    @NonNull
    private static AreaRepository provideAreaRepository(
            MealsLocalDataSourceImpl local,
            MealsRemoteDataSourceImpl remote
    ) {
        return new AreaRepository(local, remote);
    }

    @NonNull
    private static MealRepository provideMealRepository(
            MealsRemoteDataSourceImpl remote
    ) {
        return new MealRepository(remote);
    }

    // Presenters

    @NonNull
    public static HomePresenter provideHomePresenter(
            HomeContract.View view,
            Context context
    ) {

        MealsLocalDataSourceImpl local = provideLocalDataSource(context);
        MealsRemoteDataSourceImpl remote = provideRemoteDataSource();

        CategoryRepository categoryRepo =
                provideCategoryRepository(local, remote);

        AreaRepository areaRepo =
                provideAreaRepository(local, remote);

        MealRepository mealRepo =
                provideMealRepository(remote);

        return new HomePresenter(
                view,
                categoryRepo,
                areaRepo,
                mealRepo
        );
    }

    public static MealDetailsPresenter provideMealDetailsPresenter(
            MealDetailsContract.View view,
            Context context
    ) {

        MealsLocalDataSourceImpl local = provideLocalDataSource(context);
        MealsRemoteDataSourceImpl remote = provideRemoteDataSource();

        MealRepository mealRepo =
                provideMealRepository(remote);

        return new MealDetailsPresenter(
                view,
                mealRepo
        );
    }

    public static CategoriesPresenter provideCategoriesPresenter(CategoriesContract.View view, Context context) {
        MealsLocalDataSourceImpl local = provideLocalDataSource(context);
        MealsRemoteDataSourceImpl remote = provideRemoteDataSource();

        CategoryRepository categoryRepo =
                provideCategoryRepository(local, remote);

        return new CategoriesPresenter(
                view,
                categoryRepo
        );
    }

    public static CategoryMealsPresenter provideCategoryMealsPresenter(CategoryMealsContract.View view) {
        MealsRemoteDataSourceImpl remote = provideRemoteDataSource();

        MealRepository mealRepo =
                provideMealRepository(remote);

        return new CategoryMealsPresenter(
                view,
                mealRepo
        );
    }

    public static AreaMealsPresenter provideAreaMealsPresenter(AreaMealsContract.View view) {
        MealsRemoteDataSourceImpl remote = provideRemoteDataSource();

        MealRepository mealRepo =
                provideMealRepository(remote);

        return new AreaMealsPresenter(
                view,
                mealRepo
        );
    }
}

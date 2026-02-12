package com.example.mealsplanner.core;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.mealsplanner.data.repository.AreaRepository;
import com.example.mealsplanner.data.repository.CategoryRepository;
import com.example.mealsplanner.data.repository.FavoriteRepository;
import com.example.mealsplanner.data.repository.MealRepository;
import com.example.mealsplanner.data.repository.PlannedMealsRepository;
import com.example.mealsplanner.data.source.local.db.AppDatabase;
import com.example.mealsplanner.data.source.local.favorite.FavoriteLocalDataSourceImpl;
import com.example.mealsplanner.data.source.local.mealsource.MealsLocalDataSourceImpl;
import com.example.mealsplanner.data.source.planner.PlannedMealLocalDataSourceImpl;
import com.example.mealsplanner.data.source.remote.api.RetrofitClient;
import com.example.mealsplanner.data.source.remote.favorite.FavoriteFirebaseDataSource;
import com.example.mealsplanner.data.source.remote.favorite.FavoriteRemoteDataSource;
import com.example.mealsplanner.data.source.remote.mealsource.MealsRemoteDataSourceImpl;
import com.example.mealsplanner.data.source.remote.planner.PlannedMealRemoteDataSourceImpl;
import com.example.mealsplanner.presentation.main.areameals.contract.AreaMealsContract;
import com.example.mealsplanner.presentation.main.areameals.presenter.AreaMealsPresenter;
import com.example.mealsplanner.presentation.main.categories.contract.CategoriesContract;
import com.example.mealsplanner.presentation.main.categories.presenter.CategoriesPresenter;
import com.example.mealsplanner.presentation.main.categorymeals.contract.CategoryMealsContract;
import com.example.mealsplanner.presentation.main.categorymeals.presenter.CategoryMealsPresenter;
import com.example.mealsplanner.presentation.main.favorites.contract.FavoriteContract;
import com.example.mealsplanner.presentation.main.favorites.presenter.FavoritePresenter;
import com.example.mealsplanner.presentation.main.home.contract.HomeContract;
import com.example.mealsplanner.presentation.main.home.presenter.HomePresenter;
import com.example.mealsplanner.presentation.main.mealdetails.contract.MealDetailsContract;
import com.example.mealsplanner.presentation.main.mealdetails.presenter.MealDetailsPresenter;
import com.example.mealsplanner.presentation.main.planner.contract.PlannerContract;
import com.example.mealsplanner.presentation.main.planner.presenter.PlannerPresenter;
import com.example.mealsplanner.presentation.main.search.contract.SearchContract;
import com.example.mealsplanner.presentation.main.search.presenter.SearchPresenter;

public final class AppInjection {

    private AppInjection() {
    }

    // Data Sources
    private static MealsLocalDataSourceImpl provideMealsLocal(Context context) {
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());
        return new MealsLocalDataSourceImpl(
                db.getAreaDAO(),
                db.getCategoryDAO(),
                db.getPlannedMealDAO(),
                db.getFavoriteMealDAO()
        );
    }

    private static MealsRemoteDataSourceImpl provideMealsRemote() {
        return new MealsRemoteDataSourceImpl(RetrofitClient.getInstance().getApiService());
    }

    private static FavoriteLocalDataSourceImpl provideFavoriteLocal(Context context) {
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());
        return new FavoriteLocalDataSourceImpl(
                db.getFavoriteMealDAO(),
                BaseApplication.getInstance().session().getUserId()
        );
    }

    private static FavoriteRemoteDataSource provideFavoriteRemote() {
        return new FavoriteFirebaseDataSource();
    }

    private static PlannedMealLocalDataSourceImpl providePlannedMealLocal(Context context) {
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());
        return new PlannedMealLocalDataSourceImpl(db.getPlannedMealDAO());
    }

    private static PlannedMealRemoteDataSourceImpl providePlannedMealRemote() {
        return new PlannedMealRemoteDataSourceImpl();
    }

    // Repositories

    private static CategoryRepository provideCategoryRepo(MealsLocalDataSourceImpl local, MealsRemoteDataSourceImpl remote) {
        return new CategoryRepository(local, remote);
    }

    private static AreaRepository provideAreaRepo(MealsLocalDataSourceImpl local, MealsRemoteDataSourceImpl remote) {
        return new AreaRepository(local, remote);
    }

    private static MealRepository provideMealRepo(MealsRemoteDataSourceImpl remote) {
        return new MealRepository(remote);
    }

    private static FavoriteRepository provideFavoriteRepo(Context context) {
        FavoriteLocalDataSourceImpl local = provideFavoriteLocal(context);
        FavoriteRemoteDataSource remote = provideFavoriteRemote();
        return new FavoriteRepository(local, remote, BaseApplication.getInstance().session().getUserId());
    }

    private static PlannedMealsRepository providePlannedMealsRepo(Context context) {
        return new PlannedMealsRepository(
                providePlannedMealLocal(context),
                providePlannedMealRemote()
        );
    }

    // Presenters


    @NonNull
    public static HomePresenter provideHomePresenter(HomeContract.View view, Context context) {
        MealsLocalDataSourceImpl local = provideMealsLocal(context);
        MealsRemoteDataSourceImpl remote = provideMealsRemote();
        return new HomePresenter(
                view,
                provideCategoryRepo(local, remote),
                provideAreaRepo(local, remote),
                provideMealRepo(remote)
        );
    }

    @NonNull
    public static MealDetailsPresenter provideMealDetailsPresenter(MealDetailsContract.View view, Context context) {
        MealsRemoteDataSourceImpl remote = provideMealsRemote();

        return new MealDetailsPresenter(
                view,
                provideMealRepo(remote),
                provideFavoriteRepo(context),
                providePlannedMealsRepo(context),
                BaseApplication.getInstance().session().getUserId()
        );
    }

    @NonNull
    public static CategoriesPresenter provideCategoriesPresenter(CategoriesContract.View view, Context context) {
        MealsLocalDataSourceImpl local = provideMealsLocal(context);
        MealsRemoteDataSourceImpl remote = provideMealsRemote();
        return new CategoriesPresenter(
                view,
                provideCategoryRepo(local, remote)
        );
    }

    @NonNull
    public static CategoryMealsPresenter provideCategoryMealsPresenter(CategoryMealsContract.View view) {
        return new CategoryMealsPresenter(view, provideMealRepo(provideMealsRemote()));
    }

    @NonNull
    public static AreaMealsPresenter provideAreaMealsPresenter(AreaMealsContract.View view) {
        return new AreaMealsPresenter(view, provideMealRepo(provideMealsRemote()));
    }

    @NonNull
    public static FavoritePresenter provideFavoritePresenter(FavoriteContract.View view, Context context) {
        return new FavoritePresenter(view, provideFavoriteRepo(context));
    }

    @NonNull
    public static SearchPresenter provideSearchPresenter(SearchContract.View view, Context context) {
        MealsLocalDataSourceImpl local = provideMealsLocal(context);
        MealsRemoteDataSourceImpl remote = provideMealsRemote();
        return new SearchPresenter(
                view,
                provideMealRepo(remote),
                provideCategoryRepo(local, remote),
                provideAreaRepo(local, remote)
        );
    }

    public static PlannerContract.Presenter providePlannerPresenter(PlannerContract.View view, Context context) {
        return new PlannerPresenter(
                view,
                providePlannedMealsRepo(context),
                BaseApplication.getInstance().session().getUserId()
        );
    }
}

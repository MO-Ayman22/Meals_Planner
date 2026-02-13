package com.example.mealsplanner.core;

import android.content.Context;

import androidx.annotation.NonNull;

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

public final class PresenterProvider {

    private PresenterProvider() {
    }

    @NonNull
    public static HomePresenter provideHomePresenter(HomeContract.View view, Context context) {
        return new HomePresenter(
                view,
                RepositoryProvider.provideCategoryRepo(context),
                RepositoryProvider.provideAreaRepo(context),
                RepositoryProvider.provideMealRepo(context)
        );
    }

    @NonNull
    public static MealDetailsPresenter provideMealDetailsPresenter(MealDetailsContract.View view, Context context) {
        return new MealDetailsPresenter(
                view,
                RepositoryProvider.provideMealRepo(context),
                RepositoryProvider.provideFavoriteRepo(context),
                RepositoryProvider.providePlannedMealsRepo(context),
                SessionManager.getInstance(context).getUserId()
        );
    }

    @NonNull
    public static CategoriesPresenter provideCategoriesPresenter(CategoriesContract.View view, Context context) {
        return new CategoriesPresenter(view, RepositoryProvider.provideCategoryRepo(context));
    }

    @NonNull
    public static CategoryMealsPresenter provideCategoryMealsPresenter(CategoryMealsContract.View view, Context context) {
        return new CategoryMealsPresenter(view, RepositoryProvider.provideMealRepo(context));
    }

    @NonNull
    public static AreaMealsPresenter provideAreaMealsPresenter(AreaMealsContract.View view, Context context) {
        return new AreaMealsPresenter(view, RepositoryProvider.provideMealRepo(context));
    }

    @NonNull
    public static FavoritePresenter provideFavoritePresenter(FavoriteContract.View view, Context context) {
        return new FavoritePresenter(view, RepositoryProvider.provideFavoriteRepo(context));
    }

    @NonNull
    public static SearchPresenter provideSearchPresenter(SearchContract.View view, Context context) {
        return new SearchPresenter(
                view,
                RepositoryProvider.provideMealRepo(context),
                RepositoryProvider.provideCategoryRepo(context),
                RepositoryProvider.provideAreaRepo(context)
        );
    }

    @NonNull
    public static PlannerContract.Presenter providePlannerPresenter(PlannerContract.View view, Context context) {
        return new PlannerPresenter(
                view,
                RepositoryProvider.providePlannedMealsRepo(context),
                SessionManager.getInstance(context).getUserId()
        );
    }
}

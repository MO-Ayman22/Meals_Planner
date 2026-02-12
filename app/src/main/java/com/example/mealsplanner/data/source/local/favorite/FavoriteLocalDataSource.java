package com.example.mealsplanner.data.source.local.favorite;

import com.example.mealsplanner.data.model.domain.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface FavoriteLocalDataSource {

    Flowable<List<Meal>> getFavorites();

    Completable insertFavorite(Meal meal);

    Completable deleteFavorite(String mealId);

    Single<Boolean> isFavorite(String mealId);

    Completable clearFavorites();
}

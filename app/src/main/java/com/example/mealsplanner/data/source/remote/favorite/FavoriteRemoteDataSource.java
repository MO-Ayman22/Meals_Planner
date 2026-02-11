package com.example.mealsplanner.data.source.remote.favorite;

import com.example.mealsplanner.data.model.domain.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface FavoriteRemoteDataSource {

    Completable addFavorite(String userId, Meal meal);

    Completable removeFavorite(String userId, String mealId);

    Single<List<Meal>> getFavorites(String userId);
}

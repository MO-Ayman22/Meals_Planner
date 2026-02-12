package com.example.mealsplanner.data.repository;

import com.example.mealsplanner.data.model.domain.Meal;
import com.example.mealsplanner.data.source.local.favorite.FavoriteLocalDataSource;
import com.example.mealsplanner.data.source.remote.favorite.FavoriteRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class FavoriteRepository {

    private final FavoriteLocalDataSource local;
    private final FavoriteRemoteDataSource remote;
    private final String userId;

    public FavoriteRepository(
            FavoriteLocalDataSource local,
            FavoriteRemoteDataSource remote,
            String userId
    ) {
        this.local = local;
        this.remote = remote;
        this.userId = userId;
    }


    public Flowable<List<Meal>> getFavorites() {
        return local.getFavorites();
    }

    public Completable addFavorite(Meal meal) {
        return local.insertFavorite(meal)
                .andThen(
                        remote.addFavorite(userId, meal)
                                .onErrorComplete()
                );
    }

    public Completable removeFavorite(String mealId) {
        return local.deleteFavorite(mealId)
                .andThen(
                        remote.removeFavorite(userId, mealId)
                                .onErrorComplete()
                );
    }


    public Single<Boolean> isFavorite(String mealId) {
        return local.isFavorite(mealId).onErrorReturnItem(false);
    }


    public Completable syncFavorites() {
        return remote.getFavorites(userId)
                .flatMapCompletable(remoteFavorites ->
                        local.clearFavorites()
                                .andThen(
                                        Completable.fromAction(() -> {
                                            for (Meal meal : remoteFavorites) {
                                                local.insertFavorite(meal).blockingAwait();
                                            }
                                        })
                                )
                )
                .onErrorComplete();
    }
}

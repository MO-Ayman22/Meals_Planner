package com.example.mealsplanner.data.source.local.favorite;

import com.example.mealsplanner.data.model.domain.Meal;
import com.example.mealsplanner.data.source.local.dao.FavoriteMealDao;
import com.example.mealsplanner.util.mapper.MealMapper;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class FavoriteLocalDataSourceImpl
        implements FavoriteLocalDataSource {

    private final FavoriteMealDao dao;
    private final String userId;

    public FavoriteLocalDataSourceImpl(
            FavoriteMealDao dao,
            String userId
    ) {
        this.dao = dao;
        this.userId = userId;
    }

    @Override
    public Flowable<List<Meal>> getFavorites() {
        return dao.getFavorites(userId)
                .map(MealMapper::fromFavEntities);
    }

    @Override
    public Completable insertFavorite(Meal meal) {
        return dao.insert(
                MealMapper.toFavEntity(meal, userId)
        );
    }

    @Override
    public Completable deleteFavorite(String mealId) {
        return dao.delete(userId, mealId);
    }

    @Override
    public Single<Boolean> isFavorite(String mealId) {
        return dao.isFavorite(userId, mealId);
    }

    @Override
    public Completable clearFavorites() {
        return dao.clear(userId);
    }
}

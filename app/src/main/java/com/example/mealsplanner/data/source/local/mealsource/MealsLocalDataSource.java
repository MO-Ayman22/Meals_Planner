package com.example.mealsplanner.data.source.local.mealsource;

import com.example.mealsplanner.data.domain.entity.AreaEntity;
import com.example.mealsplanner.data.domain.entity.CategoryEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface MealsLocalDataSource {

    Single<List<AreaEntity>> getAreas();

    Completable insertAreas(List<AreaEntity> areas);

    Completable clearAreas();

    Single<List<CategoryEntity>> getCategories();

    Completable insertCategories(List<CategoryEntity> categories);

    Completable clearCategories();


}

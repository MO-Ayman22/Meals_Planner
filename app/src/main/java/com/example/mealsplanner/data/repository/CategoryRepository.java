package com.example.mealsplanner.data.repository;

import com.example.mealsplanner.data.domain.entity.CategoryEntity;
import com.example.mealsplanner.data.domain.model.Category;
import com.example.mealsplanner.data.source.local.mealsource.MealsLocalDataSource;
import com.example.mealsplanner.data.source.remote.mealsource.MealsRemoteDataSource;
import com.example.mealsplanner.util.mapper.CategoryMapper;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class CategoryRepository {

    private final MealsLocalDataSource local;
    private final MealsRemoteDataSource remote;

    public CategoryRepository(MealsLocalDataSource local,
                              MealsRemoteDataSource remote) {
        this.local = local;
        this.remote = remote;
    }

    public Flowable<List<Category>> getCategories() {

        Flowable<List<Category>> localFlow =
                local.getCategories()
                        .map(CategoryMapper::fromEntities)
                        .toFlowable();

        Flowable<List<Category>> remoteFlow =
                remote.getCategories()
                        .toFlowable()
                        .flatMap(dtos -> {

                            List<CategoryEntity> newEntities =
                                    CategoryMapper.dtosToEntities(dtos);

                            return local.getCategories()
                                    .toFlowable()
                                    .flatMap(oldEntities -> {


                                        return local.clearCategories()
                                                .andThen(
                                                        local.insertCategories(newEntities)
                                                )
                                                .andThen(
                                                        Flowable.just(
                                                                CategoryMapper.fromDtos(dtos)
                                                        )
                                                );
                                    });
                        }).onErrorResumeNext(error -> {
                            return Flowable.empty();
                        });

        return localFlow.concatWith(remoteFlow);
    }
}


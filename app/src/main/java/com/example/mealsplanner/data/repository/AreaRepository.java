package com.example.mealsplanner.data.repository;

import com.example.mealsplanner.data.model.domain.Area;
import com.example.mealsplanner.data.model.entity.AreaEntity;
import com.example.mealsplanner.data.source.local.mealsource.MealsLocalDataSource;
import com.example.mealsplanner.data.source.remote.mealsource.MealsRemoteDataSource;
import com.example.mealsplanner.util.mapper.AreaMapper;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class AreaRepository {
    private final MealsLocalDataSource local;
    private final MealsRemoteDataSource remote;

    public AreaRepository(MealsLocalDataSource local,
                          MealsRemoteDataSource remote) {
        this.local = local;
        this.remote = remote;
    }

    public Flowable<List<Area>> getAreas() {

        Flowable<List<Area>> localFlow =
                local.getAreas()
                        .map(AreaMapper::fromEntities)
                        .toFlowable();

        Flowable<List<Area>> remoteFlow =
                remote.getAreas()
                        .toFlowable()
                        .flatMap(dtos -> {

                            List<AreaEntity> newEntities =
                                    AreaMapper.dtosToEntities(dtos);

                            return local.getAreas()
                                    .toFlowable()
                                    .flatMap(oldEntities -> local.clearAreas()
                                            .andThen(
                                                    local.insertAreas(newEntities)
                                            )
                                            .andThen(
                                                    Flowable.just(
                                                            AreaMapper.fromDtos(dtos)
                                                    )
                                            ));
                        });

        return localFlow.concatWith(remoteFlow);
    }
}

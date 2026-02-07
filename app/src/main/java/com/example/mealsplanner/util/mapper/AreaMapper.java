package com.example.mealsplanner.util.mapper;

import com.example.mealsplanner.data.model.domain.Area;
import com.example.mealsplanner.data.model.dto.AreaDto;
import com.example.mealsplanner.data.model.entity.AreaEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AreaMapper {

    public static Area fromDto(AreaDto dto) {
        return new Area(dto.getName());
    }

    public static Area fromEntity(AreaEntity entity) {
        return new Area(entity.getName());
    }

    public static List<Area> fromEntities(List<AreaEntity> entities) {
        return Observable.fromIterable(entities)
                .subscribeOn(Schedulers.computation())
                .map(AreaMapper::fromEntity)
                .toList()
                .blockingGet();
    }

    public static AreaEntity toEntity(Area area) {
        return new AreaEntity(area.getName());
    }

    public static List<AreaEntity> toEntities(List<Area> areas) {
        return Observable.fromIterable(areas)
                .subscribeOn(Schedulers.computation())
                .map(AreaMapper::toEntity)
                .toList()
                .blockingGet();
    }
}

package com.example.mealsplanner.util.mapper;

import com.example.mealsplanner.data.domain.dto.AreaDto;
import com.example.mealsplanner.data.domain.entity.AreaEntity;
import com.example.mealsplanner.data.domain.model.Area;

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

    public static AreaEntity dtoToEntity(AreaDto area) {
        return new AreaEntity(area.getName());
    }

    public static List<AreaEntity> toEntities(List<Area> areas) {
        return Observable.fromIterable(areas)
                .subscribeOn(Schedulers.computation())
                .map(AreaMapper::toEntity)
                .toList()
                .blockingGet();
    }

    public static List<AreaEntity> dtosToEntities(List<AreaDto> areas) {
        return Observable.fromIterable(areas)
                .subscribeOn(Schedulers.computation())
                .map(AreaMapper::dtoToEntity)
                .toList()
                .blockingGet();
    }

    public static List<Area> fromDtos(List<AreaDto> dtos) {
        return Observable.fromIterable(dtos)
                .subscribeOn(Schedulers.computation())
                .map(AreaMapper::fromDto)
                .toList()
                .blockingGet();
    }



}

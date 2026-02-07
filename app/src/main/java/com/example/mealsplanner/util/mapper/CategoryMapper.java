package com.example.mealsplanner.util.mapper;

import com.example.mealsplanner.data.model.domain.Category;
import com.example.mealsplanner.data.model.dto.CategoryDto;
import com.example.mealsplanner.data.model.entity.CategoryEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryMapper {

    public static Category fromDto(CategoryDto dto) {
        return new Category(
                dto.getId(),
                dto.getName(),
                dto.getImage()
        );
    }

    public static Category fromEntity(CategoryEntity entity) {
        return new Category(
                entity.getId(),
                entity.getName(),
                entity.getImage()
        );
    }

    public static CategoryEntity toEntity(Category category) {
        return new CategoryEntity(
                category.getId(),
                category.getName(),
                category.getImage()
        );
    }

    public static List<Category> fromDtoList(List<CategoryDto> dtos) {
        return Observable.fromIterable(dtos)
                .subscribeOn(Schedulers.computation())
                .map(CategoryMapper::fromDto)
                .toList()
                .blockingGet();
    }

    public static List<Category> fromEntityList(List<CategoryEntity> entities) {
        return Observable.fromIterable(entities)
                .subscribeOn(Schedulers.computation())
                .map(CategoryMapper::fromEntity)
                .toList()
                .blockingGet();
    }

    public static List<CategoryEntity> toEntityList(List<Category> categories) {
        return Observable.fromIterable(categories)
                .subscribeOn(Schedulers.computation())
                .map(CategoryMapper::toEntity)
                .toList()
                .blockingGet();
    }
}

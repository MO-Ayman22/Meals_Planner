package com.example.mealsplanner.data.source.local.mealsource;

import com.example.mealsplanner.data.model.entity.AreaEntity;
import com.example.mealsplanner.data.model.entity.CategoryEntity;
import com.example.mealsplanner.data.model.entity.FavoriteMealEntity;
import com.example.mealsplanner.data.model.entity.PlannedMealEntity;
import com.example.mealsplanner.data.source.local.dao.AreaDao;
import com.example.mealsplanner.data.source.local.dao.CategoryDao;
import com.example.mealsplanner.data.source.local.dao.FavoriteMealDao;
import com.example.mealsplanner.data.source.local.dao.PlannedMealDao;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {
    private final AreaDao areaDao;
    private final CategoryDao categoryDao;
    private final PlannedMealDao plannedMealDao;
    private final FavoriteMealDao favoriteMealDao;

    public MealsLocalDataSourceImpl(AreaDao areaDao, CategoryDao categoryDao, PlannedMealDao plannedMealDao, FavoriteMealDao favoriteMealDao) {
        this.areaDao = areaDao;
        this.categoryDao = categoryDao;
        this.plannedMealDao = plannedMealDao;
        this.favoriteMealDao = favoriteMealDao;
    }


    @Override
    public Single<List<AreaEntity>> getAreas() {
        return areaDao.getAll();
    }

    @Override
    public Completable insertAreas(List<AreaEntity> areas) {
        return areaDao.insertAll(areas);
    }

    @Override
    public Completable clearAreas() {
        return areaDao.clear();
    }

    @Override
    public Single<List<CategoryEntity>> getCategories() {
        return categoryDao.getAll();
    }

    @Override
    public Completable insertCategories(List<CategoryEntity> categories) {
        return categoryDao.insertAll(categories);
    }

    @Override
    public Completable clearCategories() {
        return categoryDao.clear();
    }

    @Override
    public Flowable<List<PlannedMealEntity>> getMealsByDay(String day) {
        return plannedMealDao.getMealsByDay(day);
    }

    @Override
    public Completable insertPlannedMeal(PlannedMealEntity meal) {
        return plannedMealDao.insert(meal);
    }

    @Override
    public Completable deletePlannedMeal(int planId) {
        return plannedMealDao.delete(planId);
    }

    @Override
    public Flowable<List<FavoriteMealEntity>> getFavoriteMeals() {
        return favoriteMealDao.getFavoriteMeals();
    }

    @Override
    public Completable insertFavoriteMeal(FavoriteMealEntity meal) {
        return null;
    }

    @Override
    public Completable deleteFavoriteMeal(String mealId) {
        return null;
    }
}

package com.example.mealsplanner.presentation.main.mealdetails.presenter;

import com.example.mealsplanner.data.domain.entity.PlannedMealEntity;
import com.example.mealsplanner.data.domain.model.Meal;
import com.example.mealsplanner.data.repository.FavoriteRepository;
import com.example.mealsplanner.data.repository.MealRepository;
import com.example.mealsplanner.data.repository.PlannedMealsRepository;
import com.example.mealsplanner.presentation.main.mealdetails.contract.MealDetailsContract;
import com.example.mealsplanner.util.mapper.MealMapper;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenter implements MealDetailsContract.Presenter {

    private final MealDetailsContract.View view;
    private final MealRepository mealRepository;
    private final FavoriteRepository favoriteRepository;
    private final PlannedMealsRepository plannedMealsRepository;
    private final CompositeDisposable disposableContainer = new CompositeDisposable();
    private final String userId;

    public MealDetailsPresenter(MealDetailsContract.View view,
                                MealRepository mealRepository,
                                FavoriteRepository favoriteRepository,
                                PlannedMealsRepository plannedMealsRepository,
                                String userId) {
        this.view = view;
        this.mealRepository = mealRepository;
        this.favoriteRepository = favoriteRepository;
        this.plannedMealsRepository = plannedMealsRepository;
        this.userId = userId;
    }

    private void addDisposable(Disposable disposable) {
        disposableContainer.add(disposable);
    }

    @Override
    public void getMeal(String mealId) {
        addDisposable(mealRepository.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showMeal, Throwable::printStackTrace));
    }

    @Override
    public void isFavorite(String mealId) {
        addDisposable(favoriteRepository.isFavorite(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showFavorite, Throwable::printStackTrace));
    }

    @Override
    public void toggleFavorite(Meal meal, boolean isFavorite) {
        if (isFavorite) {
            addDisposable(favoriteRepository.removeFavorite(meal.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> view.showMessage("Meal removed from favorites"), error -> view.showFavorite(true)));
        } else {
            addDisposable(favoriteRepository.addFavorite(meal)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> view.showMessage("Meal added to favorites"), error -> view.showFavorite(false)));
        }
    }


    public void addMealToPlanner(Meal meal, String day) {
        PlannedMealEntity plannedMeal = MealMapper.toPlannedEntity(meal, userId, day);
        addDisposable(plannedMealsRepository.insertPlannedMeal(userId, plannedMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> view.showMessage("Meal added to planner"),
                        throwable -> view.showMessage("Failed to add meal")));
    }

    @Override
    public void clear() {
        disposableContainer.clear();
    }
}

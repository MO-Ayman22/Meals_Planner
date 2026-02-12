package com.example.mealsplanner.presentation.main.areameals.presenter;

import com.example.mealsplanner.data.repository.MealRepository;
import com.example.mealsplanner.presentation.main.areameals.contract.AreaMealsContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AreaMealsPresenter implements AreaMealsContract.Presenter {
    private final AreaMealsContract.View view;

    private final MealRepository mealsRepository;

    private final CompositeDisposable disposableContainer = new CompositeDisposable();

    public AreaMealsPresenter(AreaMealsContract.View view, MealRepository mealsRepository) {
        this.view = view;
        this.mealsRepository = mealsRepository;
    }

    @Override
    public void getMealsByArea(String area) {
        Disposable disposable = mealsRepository.filterByArea(area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showMeals, Throwable::printStackTrace);
        disposableContainer.add(disposable);
    }

    @Override
    public void clear() {
        disposableContainer.clear();
    }
}

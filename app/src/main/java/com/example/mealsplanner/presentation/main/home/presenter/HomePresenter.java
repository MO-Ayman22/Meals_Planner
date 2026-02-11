package com.example.mealsplanner.presentation.main.home.presenter;

import com.example.mealsplanner.core.BaseApplication;
import com.example.mealsplanner.data.repository.AreaRepository;
import com.example.mealsplanner.data.repository.CategoryRepository;
import com.example.mealsplanner.data.repository.MealRepository;
import com.example.mealsplanner.presentation.main.home.contract.HomeContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter implements HomeContract.Presenter {
    private final HomeContract.View view;
    private final CategoryRepository categoryRepository;
    private final AreaRepository areaRepository;

    private final MealRepository mealRepository;
    private final CompositeDisposable disposableContainer = new CompositeDisposable();

    public HomePresenter(HomeContract.View view, CategoryRepository categoryRepository, AreaRepository areaRepository, MealRepository mealRepository) {
        this.view = view;
        this.categoryRepository = categoryRepository;
        this.areaRepository = areaRepository;
        this.mealRepository = mealRepository;
    }

    @Override
    public void getCategories() {
        Disposable disposable = categoryRepository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showCategories,
                        Throwable::printStackTrace);
        disposableContainer.add(disposable);
    }

    @Override
    public void getAreas() {
        Disposable disposable = areaRepository.getAreas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        view::showAreas,
                        Throwable::printStackTrace
                );
        disposableContainer.add(disposable);
    }

    public void getRandomMeal() {
        Disposable disposable = mealRepository.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                            if (BaseApplication.getInstance().session().getRandomMeal() == null) {
                                BaseApplication.getInstance().session().saveRandomMeal(meal.getId());
                            }
                            view.showRandomMeal(meal);
                        },
                        Throwable::printStackTrace
                );
        disposableContainer.add(disposable);

    }
}

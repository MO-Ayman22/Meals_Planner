package com.example.mealsplanner.presentation.main.categorymeals.presenter;

import com.example.mealsplanner.data.repository.MealRepository;
import com.example.mealsplanner.presentation.main.categorymeals.contract.CategoryMealsContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryMealsPresenter implements CategoryMealsContract.Presenter {
    private final CategoryMealsContract.View view;
    private final MealRepository mealRepository;


    private final CompositeDisposable disposableContainer = new CompositeDisposable();

    public CategoryMealsPresenter(CategoryMealsContract.View view, MealRepository mealRepository) {
        this.view = view;
        this.mealRepository = mealRepository;
    }


    @Override
    public void getMealsByCategory(String category) {
        Disposable disposable = mealRepository.filterByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showMeals
                        , Throwable::printStackTrace);
        disposableContainer.add(disposable);
    }
}

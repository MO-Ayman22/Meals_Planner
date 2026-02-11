package com.example.mealsplanner.presentation.main.mealdetails.presenter;

import com.example.mealsplanner.data.repository.MealRepository;
import com.example.mealsplanner.presentation.main.mealdetails.contract.MealDetailsContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenter implements MealDetailsContract.Presenter {

    private final MealDetailsContract.View view;
    private final MealRepository mealRepository;
    private final CompositeDisposable disposableContainer = new CompositeDisposable();

    public MealDetailsPresenter(MealDetailsContract.View view, MealRepository mealRepository) {
        this.mealRepository = mealRepository;
        this.view = view;
    }

    @Override
    public void getMeal(String mealId) {
        Disposable disposable = mealRepository.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showMeal,
                        Throwable::printStackTrace);
        disposableContainer.add(disposable);
    }

}

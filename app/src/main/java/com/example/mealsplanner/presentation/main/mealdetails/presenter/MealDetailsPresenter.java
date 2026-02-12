package com.example.mealsplanner.presentation.main.mealdetails.presenter;

import com.example.mealsplanner.data.model.domain.Meal;
import com.example.mealsplanner.data.repository.FavoriteRepository;
import com.example.mealsplanner.data.repository.MealRepository;
import com.example.mealsplanner.presentation.main.mealdetails.contract.MealDetailsContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenter implements MealDetailsContract.Presenter {

    private final MealDetailsContract.View view;
    private final MealRepository mealRepository;
    private final FavoriteRepository favoriteRepository;
    private final CompositeDisposable disposableContainer = new CompositeDisposable();

    public MealDetailsPresenter(MealDetailsContract.View view, MealRepository mealRepository, FavoriteRepository favoriteRepository) {
        this.mealRepository = mealRepository;
        this.view = view;
        this.favoriteRepository = favoriteRepository;
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

    @Override
    public void isFavorite(String mealId) {
        Disposable disposable = favoriteRepository.isFavorite(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showFavorite,
                        Throwable::printStackTrace);
        disposableContainer.add(disposable);
    }


    @Override
    public void toggleFavorite(Meal meal, boolean isFavorite) {
        if (isFavorite) {
            Disposable disposable = favoriteRepository.removeFavorite(meal.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> view.showFavorite(false),
                            Throwable::printStackTrace);
            disposableContainer.add(disposable);
        } else {
            Disposable disposable = favoriteRepository.addFavorite(meal)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> view.showFavorite(true),
                            Throwable::printStackTrace);
            disposableContainer.add(disposable);
        }
    }

}

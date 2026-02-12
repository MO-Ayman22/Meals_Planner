package com.example.mealsplanner.presentation.main.favorites.presenter;

import com.example.mealsplanner.data.repository.FavoriteRepository;
import com.example.mealsplanner.presentation.main.favorites.contract.FavoriteContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenter implements FavoriteContract.Presenter {
    private final FavoriteRepository favoriteRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final FavoriteContract.View view;

    public FavoritePresenter(FavoriteContract.View view, FavoriteRepository favoriteRepository) {
        this.view = view;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public void getFavoriteMeals() {
        Disposable disposable = favoriteRepository.getFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> view.showMeals(meals),
                        throwable -> view.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);


    }

    @Override
    public void removeFavoriteMeal(String mealId) {
        Disposable disposable = favoriteRepository.removeFavorite(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.showSuccessMessage("Meal removed from favorites");
                }, throwable -> {
                    view.showError("Something went wrong");
                });
        compositeDisposable.add(disposable);
    }

}

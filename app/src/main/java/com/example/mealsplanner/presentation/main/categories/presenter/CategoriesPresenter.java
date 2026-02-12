package com.example.mealsplanner.presentation.main.categories.presenter;

import com.example.mealsplanner.data.repository.CategoryRepository;
import com.example.mealsplanner.presentation.main.categories.contract.CategoriesContract;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class CategoriesPresenter implements CategoriesContract.Presenter {
    private final CategoriesContract.View view;
    private final CategoryRepository repository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public CategoriesPresenter(CategoriesContract.View view, CategoryRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getCategories() {
        Disposable disposable = repository.getCategories()
                .subscribe(
                        view::showCategories,
                        error -> {
                            // Handle error
                        }
                );
        this.disposable.add(disposable);
    }
}

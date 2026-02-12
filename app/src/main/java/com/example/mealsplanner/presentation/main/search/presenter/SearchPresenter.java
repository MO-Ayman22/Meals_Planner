package com.example.mealsplanner.presentation.main.search.presenter;

import android.util.Log;

import com.example.mealsplanner.core.BaseApplication;
import com.example.mealsplanner.data.domain.model.Area;
import com.example.mealsplanner.data.domain.model.Category;
import com.example.mealsplanner.data.domain.model.Meal;
import com.example.mealsplanner.data.repository.AreaRepository;
import com.example.mealsplanner.data.repository.CategoryRepository;
import com.example.mealsplanner.data.repository.MealRepository;
import com.example.mealsplanner.presentation.main.search.contract.SearchContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter implements SearchContract.Presenter {

    private final SearchContract.View view;
    private final MealRepository mealRepository;
    private final CategoryRepository categoryRepository;
    private final AreaRepository areaRepository;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final SearchFilter currentFilter = new SearchFilter();
    private List<Meal> cachedMeals = new ArrayList<>();

    public SearchPresenter(SearchContract.View view,
                           MealRepository mealRepository,
                           CategoryRepository categoryRepository,
                           AreaRepository areaRepository) {
        this.view = view;
        this.mealRepository = mealRepository;
        this.categoryRepository = categoryRepository;
        this.areaRepository = areaRepository;
    }

    @Override
    public void search(String query) {
        currentFilter.setQuery(query);
        view.showLoading();

        compositeDisposable.add(
                mealRepository.searchByName(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturnItem(new ArrayList<>())
                        .subscribe(
                                meals -> {
                                    view.hideLoading();
                                    cachedMeals = meals != null ? meals : new ArrayList<>();
                                    applyLocalFiltering();
                                },
                                throwable -> {
                                    view.hideLoading();
                                    String msg = (throwable != null && throwable.getMessage() != null)
                                            ? throwable.getMessage()
                                            : "Something went wrong. Please try again.";
                                    Log.e("SearchPresenter", "Error searching meals", throwable);
                                    view.showError(msg);
                                }
                        )
        );
    }

    @Override
    public void applyFilters(String country, String category) {
        currentFilter.setCountry(country);
        currentFilter.setCategory(category);
        applyLocalFiltering();
    }

    @Override
    public void loadFilters() {
        // Load Areas
        compositeDisposable.add(
                areaRepository.getAreas()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                areas -> {
                                    List<String> names = new ArrayList<>();
                                    for (Area area : areas) names.add(area.getName());
                                    view.showCountries(names);
                                },
                                throwable -> {
                                    Log.e("SearchPresenter", "Error loading areas", throwable);
                                    String msg = throwable != null && throwable.getMessage() != null
                                            ? throwable.getMessage()
                                            : "Failed to load countries";
                                    view.showError(msg);
                                }
                        )
        );

        // Load Categories
        compositeDisposable.add(
                categoryRepository.getCategories()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                categories -> {
                                    List<String> names = new ArrayList<>();
                                    for (Category category : categories)
                                        names.add(category.getName());
                                    view.showCategories(names);
                                },
                                throwable -> {
                                    Log.e("SearchPresenter", "Error loading categories", throwable);
                                    String msg = throwable != null && throwable.getMessage() != null
                                            ? throwable.getMessage()
                                            : "Failed to load categories";
                                    view.showError(msg);
                                }
                        )
        );
    }

    @Override
    public void internetObserve() {
        BaseApplication.getInstance().connectivity().observe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected -> {
                    if (isConnected) {
                        view.Connected();
                    } else {
                        view.LostConnection();
                    }
                });
    }

    private void applyLocalFiltering() {
        compositeDisposable.add(
                Single.fromCallable(() -> {
                            List<Meal> filtered = new ArrayList<>();
                            for (Meal meal : cachedMeals) {
                                boolean matchesCountry = currentFilter.getCountry().equals("All")
                                        || meal.getArea().equalsIgnoreCase(currentFilter.getCountry());
                                boolean matchesCategory = currentFilter.getCategory().equals("All")
                                        || meal.getCategory().equalsIgnoreCase(currentFilter.getCategory());
                                if (matchesCountry && matchesCategory) filtered.add(meal);
                            }
                            return filtered;
                        })
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                filteredMeals -> {
                                    view.showMeals(filteredMeals);
                                    view.updateResultsCount(filteredMeals.size());
                                },
                                throwable -> {
                                    Log.e("SearchPresenter", "Error applying filters", throwable);
                                    String msg = throwable != null && throwable.getMessage() != null
                                            ? throwable.getMessage()
                                            : "Error filtering meals";
                                    view.showError(msg);
                                }
                        )
        );
    }

    @Override
    public void clear() {
        compositeDisposable.clear();
    }
}

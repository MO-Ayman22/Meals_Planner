package com.example.mealsplanner.presentation.main.planner.presenter;

import com.example.mealsplanner.data.domain.entity.PlannedMealEntity;
import com.example.mealsplanner.data.domain.model.DayModel;
import com.example.mealsplanner.data.repository.PlannedMealsRepository;
import com.example.mealsplanner.presentation.main.planner.contract.PlannerContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlannerPresenter implements PlannerContract.Presenter {

    private final PlannerContract.View view;
    private final PlannedMealsRepository repository;
    private final String userId;

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final Calendar calendar = Calendar.getInstance();

    public PlannerPresenter(PlannerContract.View view,
                            PlannedMealsRepository repository,
                            String userId) {
        this.view = view;
        this.repository = repository;
        this.userId = userId;

        updateWeekRange();
    }

    @Override
    public void loadMealsForDay(String day) {
        disposable.add(
                repository.getMealsByDay(userId, day)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(meals -> {
                            if (meals.isEmpty()) view.showEmptyState();
                            else {
                                view.hideEmptyState();
                                view.showMeals(meals);
                            }
                        }, throwable -> view.showMessage("Failed to load meals"))
        );
    }


    private void updateWeek() {

        Calendar temp = (Calendar) calendar.clone();
        temp.set(Calendar.DAY_OF_WEEK, temp.getFirstDayOfWeek());

        List<DayModel> week = generateWeek(temp);

        view.updateWeek(week);

        updateWeekRange();
    }

    @Override
    public void nextWeek() {
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        updateWeek();
    }

    @Override
    public void previousWeek() {
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        updateWeek();
    }

    @Override
    public void deleteMeal(PlannedMealEntity meal) {
        Disposable disposable = repository.deletePlannedMeal(userId, meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() ->
                                view.showMessage("Meal deleted successfully"),
                        throwable ->
                                view.showMessage("Failed to delete meal")
                );
        this.disposable.add(disposable);
    }

    private void updateWeekRange() {

        Calendar temp = (Calendar) calendar.clone();
        temp.set(Calendar.DAY_OF_WEEK, temp.getFirstDayOfWeek());

        SimpleDateFormat format =
                new SimpleDateFormat("MMM dd", Locale.getDefault());

        String start = format.format(temp.getTime());

        temp.add(Calendar.DAY_OF_MONTH, 6);

        String end = format.format(temp.getTime());

        view.updateWeekRange(start + " - " + end);
    }

    private List<DayModel> generateWeek(Calendar calendar) {
        List<DayModel> week = new ArrayList<>();

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        SimpleDateFormat dayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        for (int i = 0; i < 7; i++) {

            String dayName = dayNameFormat.format(calendar.getTime());
            String dayNumber = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String fullDate = fullDateFormat.format(calendar.getTime());

            week.add(new DayModel(dayName, dayNumber, fullDate, i == 0));

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return week;
    }

    @Override
    public void clear() {
        disposable.clear();
    }
}

package com.example.mealsplanner.presentation.main.planner.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mealsplanner.core.AppInjection;
import com.example.mealsplanner.data.domain.entity.PlannedMealEntity;
import com.example.mealsplanner.data.domain.model.DayModel;
import com.example.mealsplanner.databinding.FragmentPlannerBinding;
import com.example.mealsplanner.presentation.main.home.adapter.DayAdapter;
import com.example.mealsplanner.presentation.main.planner.contract.PlannerContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PlannerFragment extends Fragment implements PlannerContract.View, PlannerMealsAdapter.OnPlannerMealClickListener {

    private FragmentPlannerBinding binding;
    private PlannerContract.Presenter presenter;
    private DayAdapter dayAdapter;
    private PlannerMealsAdapter plannerMealsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPlannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        presenter = AppInjection.providePlannerPresenter(this, requireContext());

        setupMealsRecycler();
        setupDaysRecycler();


        binding.imageView5.setOnClickListener(v -> presenter.nextWeek());
        binding.imageView4.setOnClickListener(v -> presenter.previousWeek());
    }

    private void setupMealsRecycler() {
        plannerMealsAdapter = new PlannerMealsAdapter(this);
        binding.rvMeals.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvMeals.setAdapter(plannerMealsAdapter);
    }


    private void setupDaysRecycler() {

        List<DayModel> week = generateWeek(Calendar.getInstance());

        dayAdapter = new DayAdapter(
                week,
                day -> presenter.loadMealsForDay(day.getFullDate())
        );

        binding.recyclerView.setAdapter(dayAdapter);

        if (!week.isEmpty()) {
            presenter.loadMealsForDay(week.get(0).getFullDate());
        }
    }


    @Override
    public void showMeals(List<PlannedMealEntity> meals) {
        plannerMealsAdapter.submitList(meals);
    }

    @Override
    public void showEmptyState() {
        binding.cvNoFavorites.setVisibility(View.VISIBLE);
        binding.rvMeals.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyState() {
        binding.cvNoFavorites.setVisibility(View.GONE);
        binding.rvMeals.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateWeekRange(String weekRange) {
        binding.tvWeekRange.setText(weekRange);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateWeek(List<DayModel> week) {
        dayAdapter.updateWeek(week);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.clear();
        binding = null;
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
    public void onMealClick(PlannedMealEntity meal) {
        NavDirections action = PlannerFragmentDirections.actionPlannerFragmentToMealDetailsFragment(meal.getMealId());
        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void onRemoveClick(PlannedMealEntity meal) {
        presenter.deleteMeal(meal);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.clear();
    }
}

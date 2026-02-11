package com.example.mealsplanner.presentation.main.areameals.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mealsplanner.core.AppInjection;
import com.example.mealsplanner.data.model.domain.MealPreview;
import com.example.mealsplanner.databinding.FragmentAreaMealsBinding;
import com.example.mealsplanner.presentation.main.areameals.contract.AreaMealsContract;
import com.example.mealsplanner.presentation.main.areameals.presenter.AreaMealsPresenter;

import java.util.ArrayList;
import java.util.List;

public class AreaMealsFragment extends Fragment implements AreaMealsContract.View, OnMealClickListener {

    FragmentAreaMealsBinding binding;
    private AreaMealsPresenter presenter;

    private MealsAdapter adapter;
    private String areaName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAreaMealsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null)
            areaName = getArguments().getString("areaName");
        else
            areaName = "null";
        setViews();
        initPresenter();
        setRecyclerView();
        presenter.getMealsByArea(areaName);
    }

    private void initPresenter() {
        presenter = AppInjection.provideAreaMealsPresenter(this);
    }

    private void setRecyclerView() {
        adapter = new MealsAdapter(requireContext(), new ArrayList<>(), this);
        binding.rvMeals.setLayoutManager(
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false)
        );
        binding.rvMeals.setHasFixedSize(true);
        binding.rvMeals.setAdapter(adapter);
    }

    private void setViews() {
        binding.areaTitle.setText(areaName);
    }

    @Override
    public void showMeals(List<MealPreview> meals) {
        adapter.submitList(meals);
    }

    @Override
    public void onMealClick(MealPreview meal) {
        NavDirections action = AreaMealsFragmentDirections.actionAreaMealsFragmentToMealDetailsFragment(meal.getId());
        Navigation.findNavController(requireView()).navigate(action);
    }
}
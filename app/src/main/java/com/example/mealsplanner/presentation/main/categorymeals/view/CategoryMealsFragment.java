package com.example.mealsplanner.presentation.main.categorymeals.view;

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

import com.example.mealsplanner.core.PresenterProvider;
import com.example.mealsplanner.data.domain.model.MealPreview;
import com.example.mealsplanner.databinding.FragmentCategoryMealsBinding;
import com.example.mealsplanner.presentation.main.categorymeals.contract.CategoryMealsContract;
import com.example.mealsplanner.presentation.main.categorymeals.presenter.CategoryMealsPresenter;

import java.util.ArrayList;
import java.util.List;

public class CategoryMealsFragment extends Fragment implements CategoryMealsContract.View, OnMealClickListener {
    private FragmentCategoryMealsBinding binding;
    private CategoryMealsPresenter presenter;

    private MealsAdapter adapter;
    private String categoryName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryMealsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null)
            categoryName = getArguments().getString("categoryName");
        else
            categoryName = "null";
        setViews();
        initPresenter();
        setRecyclerView();
        if (categoryName != null)
            presenter.getMealsByCategory(categoryName);
    }

    private void initPresenter() {
        presenter = PresenterProvider.provideCategoryMealsPresenter(this, requireContext());
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
        binding.categoryTitle.setText(categoryName);
    }

    @Override
    public void showMeals(List<MealPreview> meals) {
        adapter.submitList(meals);
    }

    @Override
    public void onMealClick(MealPreview meal) {
        NavDirections action = CategoryMealsFragmentDirections.actionCategoryMealsFragmentToMealDetailsFragment(meal.getId());
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.clear();
    }
}



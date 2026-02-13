package com.example.mealsplanner.presentation.main.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import com.bumptech.glide.Glide;
import com.example.mealsplanner.core.AppInjection;
import com.example.mealsplanner.data.domain.model.Area;
import com.example.mealsplanner.data.domain.model.Category;
import com.example.mealsplanner.data.domain.model.Meal;
import com.example.mealsplanner.databinding.FragmentHomeBinding;
import com.example.mealsplanner.presentation.main.home.contract.HomeContract;
import com.example.mealsplanner.presentation.main.home.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeContract.View, OnCategoryClickListener, OnAreaClickListener {

    private FragmentHomeBinding binding;
    private HomePresenter presenter;
    private CategoryAdapter categoryAdapter;
    private CountryAdapter areaAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerViews();

        binding.tvSeeAll.setOnClickListener(v -> navigateToCategories());

        initPresenter();
        presenter.getCategories();
        presenter.getAreas();
        presenter.getRandomMeal();
        presenter.internetObserve();

    }

    private void setupRecyclerViews() {
        // Categories RecyclerView
        categoryAdapter = new CategoryAdapter(requireContext(), new ArrayList<>(), this);
        binding.rvCategories.setLayoutManager(
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false)
        );
        PagerSnapHelper categorySnapHelper = new PagerSnapHelper();
        if (binding.rvCategories.getOnFlingListener() == null) {
            categorySnapHelper.attachToRecyclerView(binding.rvCategories);
        }
        binding.rvCategories.setHasFixedSize(true);
        binding.rvCategories.setAdapter(categoryAdapter);

        // Areas RecyclerView
        areaAdapter = new CountryAdapter(requireContext(), new ArrayList<>(), this);
        binding.rvCountries.setLayoutManager(
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false)
        );
        PagerSnapHelper areaSnapHelper = new PagerSnapHelper();
        if (binding.rvCountries.getOnFlingListener() == null) {
            areaSnapHelper.attachToRecyclerView(binding.rvCountries);
        }
        binding.rvCountries.setHasFixedSize(true);
        binding.rvCountries.setAdapter(areaAdapter);
    }


    private void initPresenter() {
        presenter = AppInjection.provideHomePresenter(this, requireContext());
    }

    private void navigateToCategories() {

        NavDirections action = HomeFragmentDirections.actionHomeFragmentToCategoriesFragment();
        NavHostFragment.findNavController(this).navigate(action);
    }


    @Override
    public void showCategories(List<Category> categories) {
        categoryAdapter.submitList(categories);
    }

    @Override
    public void showAreas(List<Area> areas) {
        Log.d("areas", "areas size: " + areas.size());
        areaAdapter.submitList(areas);
    }

    @Override
    public void showRandomMeal(Meal meal) {
        binding.tvMealName.setText(meal.getName());
        binding.tvMealCategory.setText(meal.getCategory());
        binding.tvMealArea.setText(meal.getArea());
        binding.tvMealDescription.setText(meal.getInstructions());
        Glide.with(requireContext())
                .load(meal.getImage())
                .into(binding.imgRandomMeal);
        binding.tvViewRecipes.setOnClickListener(v -> {
            NavDirections action = HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(meal.getId());
            NavHostFragment.findNavController(this).navigate(action);
        });
    }

    @Override
    public void onCategoryClick(Category category) {
        NavDirections action = HomeFragmentDirections.actionHomeFragmentToCategoryMealsFragment(category.getName());
        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void onAreaClick(Area area) {
        NavDirections action = HomeFragmentDirections.actionHomeFragmentToAreaMealsFragment(area.getName());
        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void LostConnection() {
        if (binding == null) return;
        binding.noConnection.setVisibility(View.VISIBLE);
        binding.connectedContainer.setVisibility(View.GONE);
    }

    @Override
    public void Connected() {
        if (binding == null) return;
        binding.noConnection.setVisibility(View.GONE);
        binding.connectedContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.clear();
    }
}
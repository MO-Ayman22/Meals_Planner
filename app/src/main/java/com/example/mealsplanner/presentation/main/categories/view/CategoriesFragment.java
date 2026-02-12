package com.example.mealsplanner.presentation.main.categories.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.mealsplanner.core.AppInjection;
import com.example.mealsplanner.data.domain.model.Category;
import com.example.mealsplanner.databinding.FragmentCategoriesBinding;
import com.example.mealsplanner.presentation.main.categories.contract.CategoriesContract;
import com.example.mealsplanner.presentation.main.categories.presenter.CategoriesPresenter;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment implements CategoriesContract.View, OnItemClickListener {

    private FragmentCategoriesBinding binding;
    private GridCategoryAdapter adapter;
    private CategoriesPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView();
        initPresenter();
        presenter.getCategories();

    }

    private void initPresenter() {
        presenter = AppInjection.provideCategoriesPresenter(this, requireContext());
    }

    private void setRecyclerView() {
        adapter = new GridCategoryAdapter(requireContext(), new ArrayList<>(), this);
        binding.recyclerCategories.setLayoutManager(
                new GridLayoutManager(requireContext(), 2));
        binding.recyclerCategories.setHasFixedSize(true);
        binding.recyclerCategories.setAdapter(adapter);
    }

    @Override
    public void showCategories(List<Category> categories) {
        adapter.submitList(categories);
    }

    @Override
    public void onItemClick(Category category) {
        NavDirections action = CategoriesFragmentDirections
                .actionCategoriesFragmentToCategoryMealsFragment(category.getName());
        NavHostFragment.findNavController(this).navigate(action);
    }
}

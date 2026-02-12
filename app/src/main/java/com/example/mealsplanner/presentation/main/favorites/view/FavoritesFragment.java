package com.example.mealsplanner.presentation.main.favorites.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mealsplanner.core.AppInjection;
import com.example.mealsplanner.data.model.domain.Meal;
import com.example.mealsplanner.databinding.FragmentFavoritesBinding;
import com.example.mealsplanner.presentation.main.favorites.contract.FavoriteContract;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment implements FavoriteContract.View, OnFavoriteClickListener {

    private FavoriteContract.Presenter presenter;
    private FragmentFavoritesBinding binding;
    private FavoriteAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initPresenter();
        presenter.getFavoriteMeals();
    }

    private void initPresenter() {
        presenter = AppInjection.provideFavoritePresenter(this, requireContext());
    }

    private void initRecyclerView() {
        adapter = new FavoriteAdapter(requireContext(), new ArrayList<>(), this);
        binding.rvFavourites.setHasFixedSize(true);
        binding.rvFavourites.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvFavourites.setAdapter(adapter);
    }

    @Override
    public void showMeals(List<Meal> meals) {
        if (meals.isEmpty()) {
            binding.rvFavourites.setVisibility(View.GONE);
            binding.cvNoFavorites.setVisibility(View.VISIBLE);
        } else {
            binding.rvFavourites.setVisibility(View.VISIBLE);
            binding.cvNoFavorites.setVisibility(View.GONE);
            adapter.submitList(meals);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFavouriteClick(Meal meal) {
        presenter.removeFavoriteMeal(meal.getId());

    }
}
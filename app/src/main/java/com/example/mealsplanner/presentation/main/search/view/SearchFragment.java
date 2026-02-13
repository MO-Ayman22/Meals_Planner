package com.example.mealsplanner.presentation.main.search.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mealsplanner.R;
import com.example.mealsplanner.core.PresenterProvider;
import com.example.mealsplanner.data.domain.model.Meal;
import com.example.mealsplanner.databinding.FragmentSearchBinding;
import com.example.mealsplanner.presentation.main.search.contract.SearchContract;
import com.example.mealsplanner.presentation.main.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment
        implements SearchContract.View,
        SearchMealsAdapter.OnSearchMealClickListener {

    private final List<String> countries = new ArrayList<>();
    private final List<String> categories = new ArrayList<>();
    private FragmentSearchBinding binding;
    private SearchPresenter presenter;
    private SearchMealsAdapter adapter;
    private String selectedCountry = "All";
    private String selectedCategory = "All";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isAdded()) return;

        presenter = PresenterProvider.provideSearchPresenter(this, requireContext());
        initUI();

        if (presenter != null) {
            presenter.loadFilters();
            presenter.search("");
            presenter.internetObserve();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initUI() {
        if (binding == null) return;
        setupRecycler();
        setupSearch();
        setupFilters();
    }

    private void setupRecycler() {
        if (binding == null) return;

        adapter = new SearchMealsAdapter(requireContext(), this);
        binding.rvMeals.setHasFixedSize(true);
        binding.rvMeals.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvMeals.setAdapter(adapter);
    }

    private void setupSearch() {
        if (binding == null) return;

        binding.searchButton.setOnClickListener(v -> {
            if (binding == null) return;
            selectedCountry = (String) binding.spinnerCountry.getSelectedItem();
            selectedCategory = (String) binding.spinnerCategory.getSelectedItem();
            if (presenter != null) {
                presenter.applyFilters(selectedCountry, selectedCategory);
            }
            binding.filterSettingsContainer.setVisibility(View.GONE);
            binding.filterSettingsButton.setBackgroundResource(R.drawable.inactive_filter_bg);
            binding.searchInput.clearFocus();

            String query = binding.searchInput.getText().toString().trim();
            if (presenter != null) presenter.search(query);
        });
    }

    private void setupFilters() {
        if (binding == null) return;


        binding.filterSettingsButton.setOnClickListener(v -> {
            if (binding.filterSettingsContainer.getVisibility() == View.GONE) {
                binding.filterSettingsContainer.setVisibility(View.VISIBLE);
                binding.filterSettingsButton.setBackgroundResource(R.drawable.active_filter_bg);
            } else {
                binding.filterSettingsContainer.setVisibility(View.GONE);
                binding.filterSettingsButton.setBackgroundResource(R.drawable.inactive_filter_bg);
            }
        });


        setupSpinner(binding.spinnerCountry, countries);
        setupSpinner(binding.spinnerCategory, categories);


        binding.butApplyFilters.setOnClickListener(v -> {
            if (binding == null) return;

            selectedCountry = (String) binding.spinnerCountry.getSelectedItem();
            selectedCategory = (String) binding.spinnerCategory.getSelectedItem();

            if (presenter != null) {
                presenter.applyFilters(selectedCountry, selectedCategory);
            }

            binding.filterSettingsContainer.setVisibility(View.GONE);
            binding.filterSettingsButton.setBackgroundResource(R.drawable.inactive_filter_bg);
        });
    }

    private void setupSpinner(android.widget.Spinner spinner, List<String> data) {
        if (!isAdded() || spinner == null) return;

        if (data.isEmpty()) data.add("All");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_item,
                data
        );
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
    }


    @Override
    public void showCountries(List<String> countriesList) {
        if (binding == null || binding.spinnerCountry == null) return;
        updateSpinnerData(binding.spinnerCountry, countries, countriesList);
    }

    @Override
    public void showCategories(List<String> categoriesList) {
        if (binding == null || binding.spinnerCategory == null) return;
        updateSpinnerData(binding.spinnerCategory, categories, categoriesList);
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

    private void updateSpinnerData(android.widget.Spinner spinner,
                                   List<String> targetList,
                                   List<String> newData) {
        if (spinner == null) return;

        targetList.clear();
        targetList.add("All");
        if (newData != null) targetList.addAll(newData);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_item,
                targetList
        );
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void showMeals(List<Meal> meals) {
        if (adapter != null) adapter.submitList(meals);
    }

    @Override
    public void showError(String message) {
        if (!isAdded()) return;
        Toast.makeText(
                requireContext(),
                message != null ? message : "Something went wrong",
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void updateResultsCount(int count) {
        if (binding == null) return;
        binding.resultsCount.setText("Found " + count + " meals");
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onSearchMealClick(Meal meal) {
        if (binding == null) return;
        NavDirections action = SearchFragmentDirections
                .actionSearchFragmentToMealDetailsFragment(meal.getId());
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.clear();
    }
}


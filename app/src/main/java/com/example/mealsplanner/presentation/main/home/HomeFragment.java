package com.example.mealsplanner.presentation.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import com.example.mealsplanner.R;
import com.example.mealsplanner.data.model.CategoryModel;
import com.example.mealsplanner.data.model.CountryModel;
import com.example.mealsplanner.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvSeeAll.setOnClickListener(v -> {
            navigateToCategories();
        });
        setupCategoriesRecycler();
        setupCountriesRecycler();
    }

    private void navigateToCategories() {

        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_categoriesFragment);
    }

    private void setupCategoriesRecycler() {
        CategoryAdapter adapter =
                new CategoryAdapter(requireContext(), getDummyCategories());

        binding.rvCategories.setLayoutManager(
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false)
        );
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(binding.rvCategories);
        binding.rvCategories.setHasFixedSize(true);
        binding.rvCategories.setAdapter(adapter);
    }

    private void setupCountriesRecycler() {
        CountryAdapter adapter =
                new CountryAdapter(requireContext(), getDummyCountries());

        binding.rvCountries.setLayoutManager(
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false)
        );
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(binding.rvCountries);
        binding.rvCountries.setHasFixedSize(true);
        binding.rvCountries.setAdapter(adapter);
    }


    private List<CategoryModel> getDummyCategories() {
        List<CategoryModel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new CategoryModel("data " + i));
        }
        return list;
    }

    private List<CountryModel> getDummyCountries() {
        List<CountryModel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new CountryModel("data " + i));
        }
        return list;
    }

}
package com.example.mealsplanner.presentation.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mealsplanner.R;
import com.example.mealsplanner.databinding.ActivityMainBinding;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.bottomNav, navController);

        Set<Integer> hiddenDestinations = new HashSet<>(Arrays.asList(
                R.id.categoryMealsFragment,
                R.id.categoriesFragment,
                R.id.areaMealsFragment,
                R.id.mealDetailsFragment
        ));

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            binding.bottomNav.setVisibility(
                    hiddenDestinations.contains(destination.getId())
                            ? View.GONE
                            : View.VISIBLE
            );
        });

        binding.bottomNav.setOnItemReselectedListener(item -> {
        });
    }


}

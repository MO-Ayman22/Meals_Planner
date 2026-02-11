package com.example.mealsplanner.presentation.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
            boolean shouldHide =
                    hiddenDestinations.contains(destination.getId());

            showBottomNav(!shouldHide);
        });

        binding.bottomNav.setOnItemReselectedListener(item -> {
        });
    }

    public void showBottomNav(boolean show) {

        ConstraintLayout.LayoutParams params =
                (ConstraintLayout.LayoutParams) binding.fragmentContainerView.getLayoutParams();

        if (show) {
            binding.bottomNav.setVisibility(View.VISIBLE);

            params.bottomToTop = R.id.bottom_nav;

        } else {
            binding.bottomNav.setVisibility(View.GONE);

            params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            params.bottomToTop = ConstraintLayout.LayoutParams.UNSET;
        }

        binding.fragmentContainerView.setLayoutParams(params);
    }

}

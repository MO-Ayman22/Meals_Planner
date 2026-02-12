package com.example.mealsplanner.presentation.main.mealdetails.view;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.mealsplanner.R;
import com.example.mealsplanner.core.AppInjection;
import com.example.mealsplanner.data.model.domain.Meal;
import com.example.mealsplanner.databinding.FragmentMealDetailsBinding;
import com.example.mealsplanner.presentation.main.mealdetails.contract.MealDetailsContract;
import com.example.mealsplanner.presentation.main.mealdetails.presenter.MealDetailsPresenter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.ArrayList;
import java.util.Arrays;


public class MealDetailsFragment extends Fragment implements MealDetailsContract.View {
    private String mealId;
    private Meal meal;
    private boolean isFavorite;
    private MealDetailsPresenter presenter;

    private FragmentMealDetailsBinding binding;

    private StepsAdapter stepsAdapter;
    private IngredientsAdapter ingredientsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null)
            mealId = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();
        initPresenter();
        setupRecyclerViews();
        if (mealId != null) {
            presenter.getMeal(mealId);
            presenter.isFavorite(mealId);
        }
        binding.favButton.setOnClickListener(v -> {
            if (isFavorite) {
                Toast.makeText(requireContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
            }
            presenter.toggleFavorite(meal, isFavorite);
        });
    }

    private void setupRecyclerViews() {

        // Ingredients RecyclerView
        ingredientsAdapter = new IngredientsAdapter(requireContext(), new ArrayList<>(), new ArrayList<>());
        binding.rvIngredients.setLayoutManager(
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false));
        binding.rvIngredients.setHasFixedSize(true);
        binding.rvIngredients.setAdapter(ingredientsAdapter);

        // Steps RecyclerView
        stepsAdapter = new StepsAdapter(requireContext(), new ArrayList<>());
        binding.rvSteps.setLayoutManager(
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false));
        binding.rvSteps.setHasFixedSize(true);
        binding.rvSteps.setAdapter(stepsAdapter);

    }

    private void initPresenter() {
        presenter = AppInjection.provideMealDetailsPresenter(this, requireContext());
    }


    @Override
    public void showMeal(Meal meal) {
        this.meal = meal;
        setupViews(meal);
    }

    @Override
    public void showFavorite(Boolean isFavorite) {
        if (isFavorite) {
            binding.favButton.setImageResource(R.drawable.ic_fav_active);
        } else {
            binding.favButton.setImageResource(R.drawable.ic_fav);
        }
        this.isFavorite = isFavorite;
    }

    private void setupViews(Meal meal) {
        binding.tvMealTitle.setText(meal.getName());
        binding.tvMealCategory.setText(meal.getCategory());
        Glide.with(binding.ivMeal)
                .load(meal.getImage())
                .placeholder(R.drawable.outline_arrow_circle_down_24)
                .error(R.drawable.outline_cloud_alert_24)
                .into(binding.ivMeal);
        ingredientsAdapter.submitList(meal.getIngredients(), meal.getMeasures());
        stepsAdapter.submitList(Arrays.asList(meal.getInstructions().split("[.]")));
        Uri uri = Uri.parse(meal.getYoutube());
        String videoId = uri.getQueryParameter("v");

        binding.cookingVideo.addYouTubePlayerListener(
                new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer player) {
                        if (videoId != null) {
                            player.cueVideo(videoId, 0);
                        }
                    }
                }
        );
        binding.playButton.setOnClickListener(v -> {
            binding.cookingVideo.setVisibility(View.VISIBLE);
            binding.playButton.setVisibility(View.GONE);
        });

    }


}
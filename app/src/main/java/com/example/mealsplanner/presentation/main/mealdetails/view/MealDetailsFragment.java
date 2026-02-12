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
import com.example.mealsplanner.core.BaseApplication;
import com.example.mealsplanner.data.domain.model.Meal;
import com.example.mealsplanner.databinding.FragmentMealDetailsBinding;
import com.example.mealsplanner.presentation.main.mealdetails.contract.MealDetailsContract;
import com.example.mealsplanner.presentation.main.mealdetails.presenter.MealDetailsPresenter;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


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
            if (BaseApplication.getInstance().session().getUserId() == null) {
                Toast.makeText(requireContext(), "Please login first", Toast.LENGTH_SHORT).show();
                return;
            }
            if (meal == null) return;

            isFavorite = !isFavorite;
            showFavorite(isFavorite);

            presenter.toggleFavorite(meal, !isFavorite); // pass previous state
        });
        binding.addToPlanner.setOnClickListener(v -> {
            if (BaseApplication.getInstance().session().getUserId() == null) {
                Toast.makeText(requireContext(), "Please login first", Toast.LENGTH_SHORT).show();
                return;
            }
            showDatePicker();
            binding.addToPlanner.setScaleX(0.8f);
            binding.addToPlanner.setScaleY(0.8f);
            binding.addToPlanner.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(50)
                    .setInterpolator(new android.view.animation.OvershootInterpolator())
                    .start();
        });

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
        this.isFavorite = isFavorite;

        binding.favButton.setImageResource(
                isFavorite ? R.drawable.ic_fav_active : R.drawable.ic_fav
        );

        binding.favButton.setScaleX(0f);
        binding.favButton.setScaleY(0f);
        binding.favButton.setAlpha(0f);

        binding.favButton.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(300)
                .setInterpolator(new android.view.animation.OvershootInterpolator())
                .start();
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
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
        setupYouTubePlayer(meal.getYoutube());

    }

    private void setupYouTubePlayer(String youtubeUrl) {
        Uri uri = Uri.parse(youtubeUrl);
        String videoId = uri.getQueryParameter("v");

        binding.cookingVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer player) {
                if (videoId != null) player.cueVideo(videoId, 0);
            }
        });
    }

    private void setupRecyclerViews() {
        ingredientsAdapter = new IngredientsAdapter(requireContext(), new ArrayList<>(), new ArrayList<>());
        stepsAdapter = new StepsAdapter(requireContext(), new ArrayList<>());

        binding.rvIngredients.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvIngredients.setAdapter(ingredientsAdapter);
        binding.rvIngredients.setHasFixedSize(true);

        binding.rvSteps.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvSteps.setAdapter(stepsAdapter);
        binding.rvSteps.setHasFixedSize(true);
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long todayInMillis = calendar.getTimeInMillis();

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(todayInMillis)
                .setCalendarConstraints(new CalendarConstraints.Builder()
                        .setStart(todayInMillis)
                        .setValidator(DateValidatorPointForward.from(todayInMillis))
                        .build())
                .build();

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Date date = new Date(selection);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getDefault());
            String selectedDate = sdf.format(date);

            if (meal != null) {
                presenter.addMealToPlanner(meal, selectedDate);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.clear();
    }
}
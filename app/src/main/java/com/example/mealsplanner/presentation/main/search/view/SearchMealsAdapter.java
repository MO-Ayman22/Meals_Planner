package com.example.mealsplanner.presentation.main.search.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsplanner.R;
import com.example.mealsplanner.data.domain.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class SearchMealsAdapter
        extends RecyclerView.Adapter<SearchMealsAdapter.MealViewHolder> {

    private final List<Meal> meals = new ArrayList<>();
    private final Context context;
    private final OnSearchMealClickListener listener;

    public SearchMealsAdapter(Context context,
                              OnSearchMealClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.bind(meal, listener);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void submitList(List<Meal> newList) {
        meals.clear();
        meals.addAll(newList);
        notifyDataSetChanged();
    }

    // Listener Interface
    public interface OnSearchMealClickListener {
        void onSearchMealClick(Meal meal);
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMeal;
        TextView tvMealTitle;
        ImageButton btnDelete;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.ivMealImage);
            tvMealTitle = itemView.findViewById(R.id.tvTitle);
            btnDelete = itemView.findViewById(R.id.ibDelete);
        }


        void bind(Meal meal, OnSearchMealClickListener listener) {
            btnDelete.setVisibility(View.GONE);
            tvMealTitle.setText(meal.getName());
            Glide.with(itemView)
                    .load(meal.getImage())
                    .placeholder(R.drawable.outline_arrow_circle_down_24)
                    .error(R.drawable.outline_arrow_circle_down_24)
                    .into(imgMeal);

            itemView.setOnClickListener(v -> listener.onSearchMealClick(meal));
        }
    }
}

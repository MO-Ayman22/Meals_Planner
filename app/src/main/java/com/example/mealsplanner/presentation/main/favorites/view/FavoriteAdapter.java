package com.example.mealsplanner.presentation.main.favorites.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsplanner.R;
import com.example.mealsplanner.data.model.domain.Meal;

import java.util.List;

public class FavoriteAdapter
        extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private final List<Meal> meals;
    private final Context context;
    private final OnFavoriteClickListener listener;

    public FavoriteAdapter(Context context,
                           List<Meal> meals,
                           OnFavoriteClickListener listener) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
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

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMeal;
        TextView tvMealTitle;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.ivMealImage);
            tvMealTitle = itemView.findViewById(R.id.tvTitle);
        }

        void bind(Meal meal,
                  com.example.mealsplanner.presentation.main.favorites.view.OnFavoriteClickListener listener) {

            tvMealTitle.setText(meal.getName());
            Glide.with(itemView)
                    .load(meal.getImage())
                    .placeholder(R.drawable.outline_arrow_circle_down_24)
                    .error(R.drawable.outline_arrow_circle_down_24)
                    .into(imgMeal);

            itemView.setOnClickListener(v ->
                    listener.onFavouriteClick(meal)
            );
        }
    }
}

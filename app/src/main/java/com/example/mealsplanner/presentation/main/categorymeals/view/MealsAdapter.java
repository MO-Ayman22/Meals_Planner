package com.example.mealsplanner.presentation.main.categorymeals.view;

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
import com.example.mealsplanner.data.domain.model.MealPreview;

import java.util.List;

public class MealsAdapter
        extends RecyclerView.Adapter<MealsAdapter.MealsViewHolder> {

    private final List<MealPreview> meals;
    private final Context context;
    private final OnMealClickListener listener;

    public MealsAdapter(Context context,
                        List<MealPreview> meals,
                        OnMealClickListener listener) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_item, parent, false);
        return new MealsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsViewHolder holder, int position) {
        MealPreview category = meals.get(position);

        holder.bind(category, listener);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void submitList(List<MealPreview> newList) {
        meals.clear();
        meals.addAll(newList);
        notifyDataSetChanged();
    }

    static class MealsViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMeal;
        TextView tvMealTitle;
        ImageButton ibDelete;

        public MealsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.ivMealImage);
            tvMealTitle = itemView.findViewById(R.id.tvTitle);
            ibDelete = itemView.findViewById(R.id.ibDelete);
        }

        void bind(MealPreview meal,
                  OnMealClickListener listener) {
            ibDelete.setVisibility(View.GONE);
            tvMealTitle.setText(meal.getName());
            Glide.with(itemView)
                    .load(meal.getImage())
                    .placeholder(R.drawable.outline_arrow_circle_down_24)
                    .error(R.drawable.outline_arrow_circle_down_24)
                    .into(imgMeal);

            itemView.setOnClickListener(v ->
                    listener.onMealClick(meal)
            );
        }
    }
}

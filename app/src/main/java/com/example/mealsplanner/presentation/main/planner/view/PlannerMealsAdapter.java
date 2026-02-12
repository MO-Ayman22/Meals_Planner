package com.example.mealsplanner.presentation.main.planner.view;

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
import com.example.mealsplanner.data.domain.entity.PlannedMealEntity;

import java.util.ArrayList;
import java.util.List;

public class PlannerMealsAdapter
        extends RecyclerView.Adapter<PlannerMealsAdapter.PlannerViewHolder> {

    private final List<PlannedMealEntity> meals = new ArrayList<>();
    private final OnPlannerMealClickListener listener;

    public PlannerMealsAdapter(OnPlannerMealClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_item, parent, false);
        return new PlannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlannerViewHolder holder, int position) {
        holder.bind(meals.get(position));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void submitList(List<PlannedMealEntity> newList) {
        meals.clear();
        meals.addAll(newList);
        notifyDataSetChanged();
    }

    public interface OnPlannerMealClickListener {
        void onMealClick(PlannedMealEntity meal);

        void onRemoveClick(PlannedMealEntity meal);
    }

    class PlannerViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMeal;
        ImageButton ibDelete;
        TextView tvMealTitle;

        public PlannerViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMeal = itemView.findViewById(R.id.ivMealImage);
            tvMealTitle = itemView.findViewById(R.id.tvTitle);

            ibDelete = itemView.findViewById(R.id.ibDelete);
        }

        void bind(PlannedMealEntity meal) {

            tvMealTitle.setText(meal.getName());

            Glide.with(itemView)
                    .load(meal.getImage())
                    .placeholder(R.drawable.outline_arrow_circle_down_24)
                    .error(R.drawable.outline_arrow_circle_down_24)
                    .into(imgMeal);

            itemView.setOnClickListener(v ->
                    listener.onMealClick(meal)
            );

            if (ibDelete != null) {
                ibDelete.setOnClickListener(v ->
                        listener.onRemoveClick(meal)
                );
            }
        }
    }
}

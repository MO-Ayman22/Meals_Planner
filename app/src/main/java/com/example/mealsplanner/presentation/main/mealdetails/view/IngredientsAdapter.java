package com.example.mealsplanner.presentation.main.mealdetails.view;

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

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private final List<String> ingredients;
    private final List<String> measures;
    private final Context context;

    public IngredientsAdapter(Context context, List<String> ingredients, List<String> measures) {
        this.context = context;
        this.ingredients = ingredients;
        this.measures = measures;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        String model = ingredients.get(position);
        String measure = measures.get(position);
        holder.tvIngredientName.setText(model);
        holder.tvIngredientMeasure.setText(measure);
        Glide.with(context)
                .load("https://www.themealdb.com/images/ingredients/" + model + ".png")
                .placeholder(R.drawable.outline_arrow_circle_down_24)
                .error(R.drawable.outline_arrow_circle_down_24)
                .into(holder.imgIngredient);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void submitList(List<String> ingredients, List<String> measures) {
        this.ingredients.clear();
        this.measures.clear();
        this.ingredients.addAll(ingredients);
        this.measures.addAll(measures);
        notifyDataSetChanged();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngredientName, tvIngredientMeasure;
        ImageView imgIngredient;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredientName = itemView.findViewById(R.id.tvIngredientName);
            tvIngredientMeasure = itemView.findViewById(R.id.tvIngredientMeasure);
            imgIngredient = itemView.findViewById(R.id.imgIngredient);
        }
    }
}

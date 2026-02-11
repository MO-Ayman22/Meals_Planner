package com.example.mealsplanner.presentation.main.categories.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsplanner.R;
import com.example.mealsplanner.data.model.domain.Category;

import java.util.List;

public class GridCategoryAdapter extends RecyclerView.Adapter<GridCategoryAdapter.GridCategoryViewHolder> {

    private final List<Category> categories;
    private final Context context;
    private final OnItemClickListener listener;
    private Integer counter = 0;

    public GridCategoryAdapter(Context context, List<Category> categories, OnItemClickListener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GridCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.grid_category_item, parent, false);
        return new GridCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridCategoryViewHolder holder, int position) {
        Category model = categories.get(position);
        holder.tvCategoryTitle.setText(model.getName());
        if (counter == 0)
            holder.categoryCardContainer.setBackgroundResource(R.drawable.category_item_bg_1);
        else if (counter == 1)
            holder.categoryCardContainer.setBackgroundResource(R.drawable.category_item_bg_2);
        else
            holder.categoryCardContainer.setBackgroundResource(R.drawable.category_item_bg_3);

        Glide.with(context)
                .load(model.getImage())
                .into(holder.imgCategory);

        counter = (counter + 1) % 3;
        holder.itemView.setOnClickListener(v -> listener.onItemClick(model));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void submitList(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }

    static class GridCategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCategory;
        TextView tvCategoryTitle;

        ConstraintLayout categoryCardContainer;


        public GridCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.imgCategory);
            tvCategoryTitle = itemView.findViewById(R.id.tvCategoryTitle);
            categoryCardContainer = itemView.findViewById(R.id.categoryCardContainer);
        }
    }
}

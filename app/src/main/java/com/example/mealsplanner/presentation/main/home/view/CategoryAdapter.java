package com.example.mealsplanner.presentation.main.home.view;

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
import com.example.mealsplanner.data.domain.model.Category;

import java.util.List;

public class CategoryAdapter
        extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final List<Category> categories;
    private final Context context;
    private final OnCategoryClickListener listener;

    public CategoryAdapter(Context context,
                           List<Category> categories,
                           OnCategoryClickListener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linear_category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);

        holder.bind(category, listener, position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void submitList(List<Category> newList) {
        categories.clear();
        categories.addAll(newList);
        notifyDataSetChanged();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCategory;
        TextView tvCategoryTitle;
        ConstraintLayout categoryCardContainer;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.imgCategory);
            tvCategoryTitle = itemView.findViewById(R.id.tvCategoryTitle);
            categoryCardContainer =
                    itemView.findViewById(R.id.categoryCardContainer);
        }

        void bind(Category category,
                  OnCategoryClickListener listener,
                  int position) {

            tvCategoryTitle.setText(category.getName());

            int bgRes = switch (position % 3) {
                case 0 -> R.drawable.category_item_bg_1;
                case 1 -> R.drawable.category_item_bg_2;
                default -> R.drawable.category_item_bg_3;
            };
            categoryCardContainer.setBackgroundResource(bgRes);

            Glide.with(itemView)
                    .load(category.getImage())
                    .into(imgCategory);

            itemView.setOnClickListener(v ->
                    listener.onCategoryClick(category)
            );
        }
    }
}

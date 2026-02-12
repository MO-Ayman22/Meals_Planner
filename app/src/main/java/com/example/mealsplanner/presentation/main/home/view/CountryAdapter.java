package com.example.mealsplanner.presentation.main.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealsplanner.R;
import com.example.mealsplanner.data.model.domain.Area;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CategoryViewHolder> {

    private final List<Area> countries;
    private final Context context;
    private final OnAreaClickListener listener;


    public CountryAdapter(Context context, List<Area> countries, OnAreaClickListener listener) {
        this.context = context;
        this.countries = countries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.linear_country_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Area model = countries.get(position);
        holder.tvCountryCode.setText(model.getName().substring(0, 2));
        holder.tvCountryName.setText(model.getName());
        holder.itemView.setOnClickListener(v -> listener.onAreaClick(model));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public void submitList(List<Area> areas) {
        this.countries.clear();
        this.countries.addAll(areas);
        notifyDataSetChanged();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountryCode, tvCountryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountryCode = itemView.findViewById(R.id.tvCountryCode);
            tvCountryName = itemView.findViewById(R.id.tvCountryName);
        }
    }
}

package com.example.mealsplanner.presentation.main.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealsplanner.R;
import com.example.mealsplanner.data.model.CountryModel;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CategoryViewHolder> {

    private final List<CountryModel> countries;
    private final Context context;

    public CountryAdapter(Context context, List<CountryModel> countries) {
        this.context = context;
        this.countries = countries;
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
        CountryModel model = countries.get(position);
    }

    @Override
    public int getItemCount() {
        return countries.size();
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

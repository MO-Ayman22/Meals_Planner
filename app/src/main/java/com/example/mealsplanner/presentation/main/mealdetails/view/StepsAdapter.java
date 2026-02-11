package com.example.mealsplanner.presentation.main.mealdetails.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealsplanner.R;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {

    private final List<String> steps;

    private final Context context;

    public StepsAdapter(Context context, List<String> steps) {
        this.context = context;
        this.steps = steps;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_step, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        String model = steps.get(position);
        model = model
                .replaceAll("(?m)(\r?\n){2,}", "\n").trim();
        holder.tvStep.setText(model);
        holder.tvStepNumber.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void submitList(List<String> steps) {
        this.steps.clear();
        this.steps.addAll(steps);
        notifyDataSetChanged();
    }

    static class StepViewHolder extends RecyclerView.ViewHolder {
        TextView tvStep, tvStepNumber;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStep = itemView.findViewById(R.id.tvStep);
            tvStepNumber = itemView.findViewById(R.id.tvStepNumber);
        }
    }
}

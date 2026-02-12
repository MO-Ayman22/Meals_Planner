package com.example.mealsplanner.presentation.main.home.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealsplanner.data.domain.model.DayModel;
import com.example.mealsplanner.databinding.DayItemBinding;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private final OnDayClickListener listener;
    private List<DayModel> dayList;
    private int selectedPosition = 0;

    public DayAdapter(List<DayModel> dayList, OnDayClickListener listener) {
        this.dayList = dayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DayItemBinding binding = DayItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new DayViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        holder.bind(dayList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public void updateWeek(List<DayModel> newWeek) {
        this.dayList = newWeek;
        selectedPosition = 0;
        notifyDataSetChanged();
    }

    public interface OnDayClickListener {
        void onDaySelected(DayModel day);
    }

    class DayViewHolder extends RecyclerView.ViewHolder {

        private final DayItemBinding binding;

        public DayViewHolder(DayItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(DayModel day, int position) {

            binding.tvDayName.setText(day.getDayName());
            binding.tvDayNumber.setText(day.getDayNumber());

            if (position == selectedPosition) {

                binding.cardDay.setCardBackgroundColor(Color.WHITE);
                binding.tvDayName.setTextColor(Color.BLACK);
                binding.tvDayNumber.setTextColor(Color.BLACK);

                // 👇 static scale (بدون animation)
                binding.cardDay.setScaleX(1.1f);
                binding.cardDay.setScaleY(1.1f);

            } else {

                binding.cardDay.setCardBackgroundColor(Color.parseColor("#26FFFFFF"));
                binding.tvDayName.setTextColor(Color.WHITE);
                binding.tvDayNumber.setTextColor(Color.WHITE);

                binding.cardDay.setScaleX(1f);
                binding.cardDay.setScaleY(1f);
            }

            binding.cardDay.setOnClickListener(v -> {

                if (selectedPosition != position) {

                    int previousPosition = selectedPosition;
                    selectedPosition = position;

                    notifyItemChanged(previousPosition);
                    notifyItemChanged(selectedPosition);

                    listener.onDaySelected(day);
                }
            });
        }

    }
}

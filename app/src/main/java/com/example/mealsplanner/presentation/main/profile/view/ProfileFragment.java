package com.example.mealsplanner.presentation.main.profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mealsplanner.core.BaseApplication;
import com.example.mealsplanner.core.CurrentUserHolder;
import com.example.mealsplanner.databinding.FragmentProfileBinding;
import com.example.mealsplanner.presentation.auth.AuthActivity;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.butLogout.setOnClickListener(v -> {
            BaseApplication.getInstance().session().logout();
            CurrentUserHolder.setUser(null);
            navigateToAuth();
        });
        setViews();
    }

    private void setViews() {
        if (CurrentUserHolder.hasUser()) {
            binding.tvName.setText(CurrentUserHolder.getUser().getName());
            binding.tvEmail.setText(CurrentUserHolder.getUser().getEmail());
        }
    }

    private void navigateToAuth() {
        if (!isAdded()) return;
        Intent intent = new Intent(requireContext(), AuthActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        requireActivity().finish();

    }
}
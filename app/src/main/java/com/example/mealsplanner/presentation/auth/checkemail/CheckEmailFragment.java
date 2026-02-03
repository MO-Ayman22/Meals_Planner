package com.example.mealsplanner.presentation.auth.checkemail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mealsplanner.R;
import com.example.mealsplanner.databinding.FragmentCheckEmailBinding;


public class CheckEmailFragment extends Fragment {

    private FragmentCheckEmailBinding binding;
    private NavController navController;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckEmailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        String email = CheckEmailFragmentArgs.fromBundle(getArguments()).getEmail();
        binding.tvUserEmail.setText(email);
        initListeners();
    }

    private void initListeners() {
        binding.butBTL.setOnClickListener(v -> {
            navController.navigate(R.id.action_checkEmailFragment_to_loginFragment);
        });
        binding.tvResendEmail.setOnClickListener(v -> {
            navController.popBackStack();
        });

    }
}
package com.example.mealsplanner.presentation.auth.forgetpass;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mealsplanner.R;
import com.example.mealsplanner.databinding.FragmentForgetPasswordBinding;


public class ForgetPasswordFragment extends Fragment implements ForgetPasswordContract.View {


    private FragmentForgetPasswordBinding binding;
    private NavController navController;

    private ForgetPasswordPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new ForgetPasswordPresenter(requireActivity().getApplication(), this);
        navController = NavHostFragment.findNavController(this);
        initListeners();
    }

    private void initListeners() {
        binding.tvBTL.setOnClickListener(v -> {
            navController.popBackStack();
        });

        binding.butResetPassword.setOnClickListener(v -> {
            presenter.onResetPasswordClicked(binding.edEmail.getText().toString().trim());
        });

        binding.edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.edEmail.getText().toString().trim().isEmpty()) {
                    binding.butResetPassword.setEnabled(false);
                    binding.overlayDisabled.setVisibility(View.VISIBLE);
                } else {
                    binding.butResetPassword.setEnabled(true);
                    binding.overlayDisabled.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });

    }

    @Override
    public void showResetPasswordButtonLoading() {
        binding.edEmail.clearFocus();
        binding.butResetPassword.setText(R.string.resetting_password_loading);
        binding.butResetPassword.setEnabled(false);
    }

    @Override
    public void hideResetPasswordButtonLoading() {
        binding.butResetPassword.setText(R.string.send_reset_link);
        binding.butResetPassword.setEnabled(true);
    }

    @Override
    public void showEmailError(String message) {
        binding.edEmail.setError(message);
    }

    @Override
    public void onResetPasswordSuccess() {
        String email = binding.edEmail.getText().toString().trim();
        NavDirections action = ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToCheckEmailFragment(email);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onResetPasswordError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
package com.example.mealsplanner.presentation.auth.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mealsplanner.R;
import com.example.mealsplanner.data.repository.AuthRepository;
import com.example.mealsplanner.data.repository.UserRepository;
import com.example.mealsplanner.data.source.local.db.AppDatabase;
import com.example.mealsplanner.data.source.local.usersource.UserLocalDataSourceImpl;
import com.example.mealsplanner.data.source.remote.auth.AuthRemoteDataSourceImpl;
import com.example.mealsplanner.data.source.remote.usersource.UserRemoteDataSourceImpl;
import com.example.mealsplanner.databinding.FragmentRegisterBinding;
import com.example.mealsplanner.presentation.main.MainActivity;


public class RegisterFragment extends Fragment implements RegisterContract.View {

    private FragmentRegisterBinding binding;
    private NavController navController;

    private RegisterPresenter presenter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new RegisterPresenter(new AuthRepository(new AuthRemoteDataSourceImpl(requireActivity().getApplication())),
                new UserRepository(new UserRemoteDataSourceImpl(),
                        new UserLocalDataSourceImpl(AppDatabase.getInstance(requireContext()).getUserDAO())),
                this);

        navController = NavHostFragment.findNavController(this);
        initListeners();

    }

    private void initListeners() {
        binding.tvLogin.setOnClickListener(v -> {
            navController.navigate(R.id.action_registerFragment_to_loginFragment);
        });
        binding.btnCreateAccount.setOnClickListener(v -> {
            String name = binding.edUserName.getText().toString();
            String email = binding.edEmail.getText().toString();
            String password = binding.edPassword.getText().toString();
            String confirmPassword = binding.edConfirmPassword.getText().toString();
            boolean isChecked = binding.checkBox.isChecked();

            presenter.onRegisterClicked(name, email, password, confirmPassword, isChecked);
        });

        binding.butGoogle.setOnClickListener(v -> presenter.onGoogleLoginClicked(requireActivity()));

    }

    @Override
    public void showRegisterButtonLoading() {
        binding.edUserName.clearFocus();
        binding.edEmail.clearFocus();
        binding.edPassword.clearFocus();
        binding.edConfirmPassword.clearFocus();
        binding.btnCreateAccount.setText(R.string.creating_account_loading);
        binding.btnCreateAccount.setEnabled(false);
    }

    @Override
    public void hideRegisterButtonLoading() {
        binding.btnCreateAccount.setText(R.string.create_account);
        binding.btnCreateAccount.setEnabled(true);
    }

    @Override
    public void showGoogleButtonLoading() {
        binding.edUserName.clearFocus();
        binding.edEmail.clearFocus();
        binding.edPassword.clearFocus();
        binding.edConfirmPassword.clearFocus();
        binding.butGoogle.setText(R.string.create_account);
        binding.butGoogle.setEnabled(false);
    }

    @Override
    public void hideGoogleButtonLoading() {
        binding.butGoogle.setText(R.string.google);
        binding.butGoogle.setEnabled(true);
    }

    @Override
    public void showNameError(String message) {
        binding.edUserName.setError(message);
    }

    @Override
    public void showEmailError(String message) {
        binding.edEmail.setError(message);
    }

    @Override
    public void showPasswordError(String message) {
        binding.edPassword.setError(message);
    }

    @Override
    public void showConfirmPasswordError(String message) {
        binding.edConfirmPassword.setError(message);
    }

    @Override
    public void onRegisterSuccess() {
        navigateToHome();
    }

    @Override
    public void onRegisterError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showTermsAndConditionsError(String termsAndConditionsError) {
        Toast.makeText(requireContext(), termsAndConditionsError, Toast.LENGTH_SHORT).show();
    }

    private void navigateToHome() {
        if (!isAdded()) return;
        Intent intent = new Intent(requireContext(), MainActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        requireActivity().finish();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.clear();
    }
}
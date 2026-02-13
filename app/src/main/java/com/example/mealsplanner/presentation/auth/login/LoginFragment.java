package com.example.mealsplanner.presentation.auth.login;

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
import com.example.mealsplanner.databinding.FragmentLoginBinding;
import com.example.mealsplanner.presentation.main.MainActivity;

public class LoginFragment extends Fragment implements LoginContract.View {

    private FragmentLoginBinding binding;
    private NavController navController;
    private LoginPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment(view);
        setListeners();
    }

    private void initFragment(View view) {
        presenter = new LoginPresenter(new AuthRepository(new AuthRemoteDataSourceImpl(requireActivity().getApplication())),
                new UserRepository(new UserRemoteDataSourceImpl(),
                        new UserLocalDataSourceImpl(AppDatabase.getInstance(requireContext()).getUserDAO())),
                this);
        navController = NavHostFragment.findNavController(this);
    }

    private void setListeners() {

        binding.butLogin.setOnClickListener(v -> presenter.onLoginClicked(
                binding.edEmail.getText().toString().trim(),
                binding.edPassword.getText().toString().trim()));

        binding.butGoogle.setOnClickListener(v -> presenter.onGoogleLoginClicked(requireActivity()));

        binding.butFacebook.setOnClickListener(v ->
                Toast.makeText(requireContext(),
                        "Not implemented yet",
                        Toast.LENGTH_SHORT).show()
        );

        binding.butGuest.setOnClickListener(v -> navigateToHome());

        binding.tvForgetPassword.setOnClickListener(v -> navController.navigate(R.id.action_loginFragment_to_forgetPasswordFragment));

        binding.tvSignUp.setOnClickListener(v -> navController.navigate(R.id.action_loginFragment_to_registerFragment));
    }


    private void navigateToHome() {
        if (!isAdded()) return;
        Intent intent = new Intent(requireContext(), MainActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        requireActivity().finish();

    }


    @Override
    public void showLoginButtonLoading() {
        binding.edEmail.clearFocus();
        binding.edPassword.clearFocus();
        binding.butLogin.setText(R.string.loggin_loading);
        binding.butLogin.setEnabled(false);
        binding.butLogin.setAlpha(0.6f);
    }

    @Override
    public void hideLoginButtonLoading() {
        binding.butLogin.setText(R.string.login);
        binding.butLogin.setEnabled(true);
        binding.butLogin.setAlpha(1f);
    }

    @Override
    public void showGoogleButtonLoading() {
        binding.butGoogle.setEnabled(false);
        binding.butGoogle.setAlpha(0.6f);
        binding.edEmail.clearFocus();
        binding.edPassword.clearFocus();
        binding.butGoogle.setText(R.string.loggin_loading);
    }

    @Override
    public void hideGoogleButtonLoading() {
        binding.butGoogle.setEnabled(true);
        binding.butGoogle.setAlpha(1f);
        binding.butGoogle.setText(R.string.google);
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
    public void onLoginSuccess() {
        navigateToHome();
    }

    @Override
    public void onLoginError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.clear();
    }
}

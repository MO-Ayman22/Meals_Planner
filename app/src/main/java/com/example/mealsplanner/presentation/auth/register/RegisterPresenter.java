package com.example.mealsplanner.presentation.auth.register;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.mealsplanner.data.model.User;
import com.example.mealsplanner.data.repository.AuthRepository;
import com.example.mealsplanner.data.repository.UserRepository;
import com.example.mealsplanner.data.source.remote.auth.FirebaseAuthSource;
import com.example.mealsplanner.data.source.remote.firestore.FirebaseFirestoreSource;
import com.example.mealsplanner.util.ValidationUtil;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPresenter implements RegisterContract.Presenter {
    private final RegisterContract.View view;
    private final AuthRepository authRepository;

    private final UserRepository userRepository;

    public RegisterPresenter(Application app, RegisterContract.View view) {
        this.view = view;
        this.authRepository = new AuthRepository(app);
        this.userRepository = new UserRepository();
    }

    @Override
    public void onRegisterClicked(String name, String email, String password, String confirmPassword, boolean isChecked) {
        view.showRegisterButtonLoading();
        if (!validateInputs(name, email, password, confirmPassword, isChecked)) return;

        userRepository.existsByEmail(email, new FirebaseFirestoreSource.ExistsCallback() {
            @Override
            public void onResult(boolean exists) {

                if (exists) {
                    view.hideRegisterButtonLoading();
                    view.showEmailError("Email already exists");
                } else {
                    register(name, email, password);
                }
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                view.hideRegisterButtonLoading();
                view.onRegisterError(e.getMessage());
            }
        });
    }

    private boolean validateInputs(String name, String email, String password, String confirmPassword, boolean isChecked) {
        String nameError = ValidationUtil.validateName(name);
        if (nameError != null) {
            view.hideRegisterButtonLoading();
            view.showNameError(nameError);
            return false;
        }

        String emailError = ValidationUtil.validateEmail(email);
        if (emailError != null) {
            view.hideRegisterButtonLoading();
            view.showEmailError(emailError);
            return false;
        }

        String passwordError = ValidationUtil.validatePassword(password);
        if (passwordError != null) {
            view.hideRegisterButtonLoading();
            view.showPasswordError(passwordError);
            return false;
        }

        String confirmPasswordError = ValidationUtil.validateConfirmPassword(password, confirmPassword);
        if (confirmPasswordError != null) {
            view.hideRegisterButtonLoading();
            view.showConfirmPasswordError(confirmPasswordError);
            return false;
        }

        String termsAndConditionsError = ValidationUtil.validateTermsAndConditions(isChecked);
        if (termsAndConditionsError != null) {
            view.hideRegisterButtonLoading();
            view.showTermsAndConditionsError(termsAndConditionsError);
            return false;
        }

        return true;
    }

    private void register(String name, String email, String password) {
        authRepository.register(email, password, name, new FirebaseAuthSource.AuthCallback() {

            @Override
            public void onSuccess(FirebaseUser user) {
                User newUser = new User(user.getUid(), name, email);
                createUser(newUser);
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                view.hideRegisterButtonLoading();
                view.onRegisterError(e.getMessage());
            }

            @Override
            public void onCancelled() {
                view.hideRegisterButtonLoading();
                view.onRegisterError("Something went wrong");
            }
        });
    }

    @Override
    public void onGoogleLoginClicked() {
        view.showGoogleButtonLoading();
        authRepository.loginWithGoogle(new FirebaseAuthSource.AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser firebaseUser) {

                userRepository.exists(firebaseUser.getUid(), new FirebaseFirestoreSource.ExistsCallback() {
                    @Override
                    public void onResult(boolean exists) {

                        if (exists) {
                            view.hideGoogleButtonLoading();
                            view.onRegisterSuccess();
                        } else {
                            User user = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail());
                            createUser(user);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.hideGoogleButtonLoading();
                        view.onRegisterError(e.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                view.hideGoogleButtonLoading();
                view.onRegisterError(e.getMessage());
            }

            @Override
            public void onCancelled() {
                view.hideGoogleButtonLoading();
            }
        });
    }


    private void createUser(User user) {
        userRepository.create(user, new FirebaseFirestoreSource.FirestoreCallback() {
            @Override
            public void onSuccess() {
                view.hideGoogleButtonLoading();
                view.onRegisterSuccess();
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                view.hideGoogleButtonLoading();
                view.onRegisterError(e.getMessage());
            }
        });
    }

}

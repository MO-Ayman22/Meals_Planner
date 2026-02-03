package com.example.mealsplanner.presentation.auth.login;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.mealsplanner.data.model.User;
import com.example.mealsplanner.data.repository.AuthRepository;
import com.example.mealsplanner.data.repository.UserRepository;
import com.example.mealsplanner.data.source.remote.auth.FirebaseAuthSource;
import com.example.mealsplanner.data.source.remote.firestore.FirebaseFirestoreSource;
import com.example.mealsplanner.util.ValidationUtil;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View view;
    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    public LoginPresenter(Application app, LoginContract.View view) {
        this.view = view;
        this.authRepository = new AuthRepository(app);
        this.userRepository = new UserRepository();
    }

    @Override
    public void onLoginClicked(String email, String password) {

        view.showLoginButtonLoading();

        if (validateInputs(email, password)) return;

        userRepository.existsByEmail(email, new FirebaseFirestoreSource.ExistsCallback() {
            @Override
            public void onResult(boolean exists) {

                if (exists) {
                    login(email, password);
                } else {
                    view.hideLoginButtonLoading();
                    view.showEmailError("User does not exist");
                }
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                view.hideLoginButtonLoading();
                view.onLoginError(e.getMessage());
            }
        });

    }

    private boolean validateInputs(String email, String password) {
        String emailError = ValidationUtil.validateEmail(email);
        if (emailError != null) {
            view.hideLoginButtonLoading();
            view.showEmailError(emailError);
            return false;
        }

        String passwordError = ValidationUtil.validatePassword(password);
        if (passwordError != null) {
            view.hideLoginButtonLoading();
            view.showPasswordError(passwordError);
            return false;
        }
        return true;
    }

    private void login(String email, String password) {
        authRepository.loginWithEmail(email, password, new FirebaseAuthSource.AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser firebaseUser) {
                view.hideLoginButtonLoading();
                view.onLoginSuccess();
            }

            @Override
            public void onFailure(Exception e) {
                view.hideLoginButtonLoading();
                view.showPasswordError("Invalid password");
            }

            @Override
            public void onCancelled() {
                view.hideLoginButtonLoading();
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
                            view.onLoginSuccess();
                        } else {
                            User user = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail());

                            createUser(user);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.hideGoogleButtonLoading();
                        view.onLoginError(e.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                view.hideGoogleButtonLoading();
                view.onLoginError(e.getMessage());
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
                view.onLoginSuccess();
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                view.hideGoogleButtonLoading();
                view.onLoginError(e.getMessage());
            }
        });
    }


}

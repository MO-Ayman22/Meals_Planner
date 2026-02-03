package com.example.mealsplanner.presentation.auth.forgetpass;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.mealsplanner.data.repository.AuthRepository;
import com.example.mealsplanner.data.repository.UserRepository;
import com.example.mealsplanner.data.source.remote.auth.FirebaseAuthSource;
import com.example.mealsplanner.data.source.remote.firestore.FirebaseFirestoreSource;
import com.example.mealsplanner.util.ValidationUtil;
import com.google.firebase.auth.FirebaseUser;

public class ForgetPasswordPresenter implements ForgetPasswordContract.Presenter {

    private final ForgetPasswordContract.View view;
    private final AuthRepository authRepository;
    private final UserRepository userRepository;


    public ForgetPasswordPresenter(Application app, ForgetPasswordContract.View view) {
        this.view = view;
        this.authRepository = new AuthRepository(app);
        this.userRepository = new UserRepository();
    }


    @Override
    public void onResetPasswordClicked(String email) {
        String emailError = ValidationUtil.validateEmail(email);
        if (emailError != null) {
            view.showEmailError(emailError);
            return;
        }
        userRepository.existsByEmail(email, new FirebaseFirestoreSource.ExistsCallback() {
            @Override
            public void onResult(boolean exists) {
                if (!exists) {
                    view.showEmailError("User does not exist");
                } else {
                    view.showResetPasswordButtonLoading();
                    authRepository.resetPassword(email, new FirebaseAuthSource.AuthCallback() {
                        @Override
                        public void onSuccess(FirebaseUser user) {
                            view.hideResetPasswordButtonLoading();
                            view.onResetPasswordSuccess();
                        }

                        @Override
                        public void onFailure(@NonNull Exception e) {
                            view.hideResetPasswordButtonLoading();
                            view.onResetPasswordError(e.getMessage());
                        }

                        @Override
                        public void onCancelled() {
                            view.hideResetPasswordButtonLoading();
                            view.onResetPasswordError("Something went wrong");
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                view.hideResetPasswordButtonLoading();
                view.onResetPasswordError(e.getMessage());
            }
        });


    }
}

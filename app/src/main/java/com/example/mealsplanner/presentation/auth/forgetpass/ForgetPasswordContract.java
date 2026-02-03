package com.example.mealsplanner.presentation.auth.forgetpass;

public interface ForgetPasswordContract {
    interface View {
        void showResetPasswordButtonLoading();

        void hideResetPasswordButtonLoading();

        void showEmailError(String message);

        void onResetPasswordSuccess();

        void onResetPasswordError(String message);
    }

    interface Presenter {
        void onResetPasswordClicked(String email);
    }
}

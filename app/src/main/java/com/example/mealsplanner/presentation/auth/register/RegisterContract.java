package com.example.mealsplanner.presentation.auth.register;

public interface RegisterContract {
    interface View {
        void showRegisterButtonLoading();

        void hideRegisterButtonLoading();

        void showGoogleButtonLoading();

        void hideGoogleButtonLoading();

        void showNameError(String message);

        void showEmailError(String message);

        void showPasswordError(String message);

        void showConfirmPasswordError(String message);

        void onRegisterSuccess();

        void onRegisterError(String message);

        void showTermsAndConditionsError(String termsAndConditionsError);
    }

    interface Presenter {

        void onGoogleLoginClicked();

        void onRegisterClicked(String name, String email, String password, String confirmPassword, boolean isChecked);
    }
}

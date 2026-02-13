package com.example.mealsplanner.presentation.auth.login;

import android.app.Activity;

public interface LoginContract {


    interface View {

        void showLoginButtonLoading();

        void hideLoginButtonLoading();

        void showGoogleButtonLoading();

        void hideGoogleButtonLoading();

        void showEmailError(String message);

        void showPasswordError(String message);

        void onLoginSuccess();

        void onLoginError(String message);

    }


    interface Presenter {
        void onGoogleLoginClicked(Activity activity);

        void onLoginClicked(String email, String password);

        void clear();
    }
}


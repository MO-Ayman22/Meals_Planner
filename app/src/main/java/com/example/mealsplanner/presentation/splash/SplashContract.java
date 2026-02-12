package com.example.mealsplanner.presentation.splash;

public interface SplashContract {

    interface View {
        void navigateToAuth();

        void navigateToMain();
    }

    interface Presenter {
        void decideNavigation();
    }
}

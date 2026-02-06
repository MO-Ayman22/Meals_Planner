package com.example.mealsplanner.presentation.splash;

import com.example.mealsplanner.core.BaseApplication;
import com.example.mealsplanner.core.SessionManager;

public class SplashPresenter implements SplashContract.Presenter {

    private final SplashContract.View view;
    private final SessionManager sessionManager;

    public SplashPresenter(SplashContract.View view) {
        this.view = view;
        this.sessionManager = BaseApplication.getInstance().session();
    }

    @Override
    public void decideNavigation() {
        if (sessionManager.isLoggedIn()) {
            view.navigateToMain();
        } else {
            view.navigateToAuth();
        }
    }
}

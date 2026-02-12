package com.example.mealsplanner.presentation.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealsplanner.R;
import com.example.mealsplanner.presentation.auth.AuthActivity;
import com.example.mealsplanner.presentation.main.MainActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    private LottieAnimationView logoAnimation;
    private LottieAnimationView loadingAnimation;

    private SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        presenter = new SplashPresenter(this);


        splashScreen.setKeepOnScreenCondition(() -> false);

        makeFullScreen();
        setContentView(R.layout.activity_splash);
        logoAnimation = findViewById(R.id.logoAnimation);
        loadingAnimation = findViewById(R.id.loadingAnimation);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            presenter.decideNavigation();
        }, 3000);

    }

    private void makeFullScreen() {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    @Override
    public void navigateToAuth() {
        cancelAnimation();
        Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    public void navigateToMain() {
        cancelAnimation();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void cancelAnimation() {
        logoAnimation.cancelAnimation();
        loadingAnimation.cancelAnimation();
    }


}


package com.example.mealsplanner.data.repository;

import android.app.Application;

import com.example.mealsplanner.data.source.remote.auth.FirebaseAuthSource;

public class AuthRepository {

    private final FirebaseAuthSource authSource;

    public AuthRepository(Application app) {
        authSource = new FirebaseAuthSource(app);
    }

    public void loginWithEmail(String email, String password,
                               FirebaseAuthSource.AuthCallback callback) {
        authSource.signInWithEmail(email, password, callback);
    }

    public void loginWithGoogle(FirebaseAuthSource.AuthCallback callback) {
        authSource.signInWithGoogle(callback);
    }

    public void register(String email, String password, String name,
                         FirebaseAuthSource.AuthCallback callback) {
        authSource.register(email, password, name, callback);
    }

    public void resetPassword(String email,
                              FirebaseAuthSource.AuthCallback callback) {
        authSource.resetPassword(email, callback);
    }
}

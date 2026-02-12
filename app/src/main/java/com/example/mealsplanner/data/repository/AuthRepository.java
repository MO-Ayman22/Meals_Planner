package com.example.mealsplanner.data.repository;

import com.example.mealsplanner.data.source.remote.auth.AuthRemoteDataSource;
import com.example.mealsplanner.data.source.remote.auth.AuthRemoteDataSourceImpl;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AuthRepository {

    private final AuthRemoteDataSource authSource;

    public AuthRepository(AuthRemoteDataSourceImpl authSource) {
        this.authSource = authSource;
    }

    public Single<FirebaseUser> loginWithEmail(String email, String password) {
        return authSource.signInWithEmail(email, password);
    }

    public Single<FirebaseUser> loginWithGoogle() {
        return authSource.signInWithGoogle();
    }

    public Single<FirebaseUser> register(String email, String password, String name) {
        return authSource.register(email, password, name);
    }

    public Completable resetPassword(String email) {
        return authSource.resetPassword(email);
    }
}

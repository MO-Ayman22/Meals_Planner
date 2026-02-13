package com.example.mealsplanner.data.source.remote.auth;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface AuthRemoteDataSource {

    Single<FirebaseUser> signInWithGoogle(Activity activity);

    Single<FirebaseUser> signInWithEmail(String email, String password);

    Single<FirebaseUser> register(String email, String password, String name);

    Completable resetPassword(String email);
}

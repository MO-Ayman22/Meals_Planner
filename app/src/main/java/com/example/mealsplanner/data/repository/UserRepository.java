package com.example.mealsplanner.data.repository;

import androidx.annotation.NonNull;

import com.example.mealsplanner.data.model.User;
import com.example.mealsplanner.data.source.remote.firestore.FirebaseFirestoreSource;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class UserRepository {

    private final FirebaseFirestoreSource firestoreSource;

    public UserRepository(FirebaseFirestoreSource firestoreSource) {
        this.firestoreSource = firestoreSource;
    }

    public Completable create(@NonNull User user) {
        return firestoreSource.createUser(user);
    }

    public Single<Boolean> exists(@NonNull String uid) {
        return firestoreSource.exists(uid);
    }

    public Single<Boolean> existsByEmail(@NonNull String email) {
        return firestoreSource.existsByEmail(email);
    }
}

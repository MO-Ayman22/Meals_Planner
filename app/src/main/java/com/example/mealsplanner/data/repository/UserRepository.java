package com.example.mealsplanner.data.repository;

import androidx.annotation.NonNull;

import com.example.mealsplanner.data.model.User;
import com.example.mealsplanner.data.source.remote.firestore.FirebaseFirestoreSource;

public class UserRepository {

    private final FirebaseFirestoreSource firestoreSource;

    public UserRepository() {
        firestoreSource = new FirebaseFirestoreSource();
    }

    public void create(@NonNull User user,
                       @NonNull FirebaseFirestoreSource.FirestoreCallback callback) {
        firestoreSource.createUser(user, callback);
    }

    public void exists(@NonNull String uid,
                       @NonNull FirebaseFirestoreSource.ExistsCallback callback) {
        firestoreSource.exists(uid, callback);
    }

    public void existsByEmail(@NonNull String email,
                              @NonNull FirebaseFirestoreSource.ExistsCallback callback) {
        firestoreSource.existsByEmail(email, callback);
    }
}

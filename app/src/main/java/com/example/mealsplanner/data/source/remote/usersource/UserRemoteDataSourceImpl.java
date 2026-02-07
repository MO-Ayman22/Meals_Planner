package com.example.mealsplanner.data.source.remote.usersource;

import androidx.annotation.NonNull;

import com.example.mealsplanner.data.model.domain.User;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class UserRemoteDataSourceImpl implements UserRemoteDataSource {

    private static final String USERS_COLLECTION = "users";
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    public Completable createUser(@NonNull User user) {
        return Completable.create(emitter ->
                firestore.collection(USERS_COLLECTION)
                        .document(user.uid())
                        .set(user)
                        .addOnSuccessListener(unused -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError)
        );
    }

    @Override
    public Single<User> getUser(String uid) {
        return Single.create(emitter ->
                firestore.collection(USERS_COLLECTION)
                        .document(uid)
                        .get()
                        .addOnSuccessListener(snapshot -> {
                            if (snapshot.exists()) {
                                User user = snapshot.toObject(User.class);
                                emitter.onSuccess(user);
                            } else {
                                emitter.onError(new Exception("User not found"));
                            }
                        })
                        .addOnFailureListener(emitter::onError)
        );
    }

    @Override
    public Single<Boolean> exists(@NonNull String uid) {
        return Single.create(emitter ->
                firestore.collection(USERS_COLLECTION)
                        .document(uid)
                        .get()
                        .addOnSuccessListener(snapshot -> emitter.onSuccess(snapshot.exists()))
                        .addOnFailureListener(emitter::onError)
        );
    }

    @Override
    public Single<Boolean> existsByEmail(@NonNull String email) {
        return Single.create(emitter ->
                firestore.collection(USERS_COLLECTION)
                        .whereEqualTo("email", email)
                        .limit(1)
                        .get()
                        .addOnSuccessListener(querySnapshot -> emitter.onSuccess(!querySnapshot.isEmpty()))
                        .addOnFailureListener(emitter::onError)
        );
    }
}

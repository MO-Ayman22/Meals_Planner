package com.example.mealsplanner.data.source.remote.firestore;

import androidx.annotation.NonNull;

import com.example.mealsplanner.data.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseFirestoreSource {

    private static final String USERS_COLLECTION = "users";
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public Completable createUser(@NonNull User user) {
        return Completable.create(emitter ->
                firestore.collection(USERS_COLLECTION)
                        .document(user.getUid())
                        .set(user)
                        .addOnSuccessListener(unused -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError)
        );
    }

    public Single<Boolean> exists(@NonNull String uid) {
        return Single.create(emitter ->
                firestore.collection(USERS_COLLECTION)
                        .document(uid)
                        .get()
                        .addOnSuccessListener(snapshot -> emitter.onSuccess(snapshot.exists()))
                        .addOnFailureListener(emitter::onError)
        );
    }

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

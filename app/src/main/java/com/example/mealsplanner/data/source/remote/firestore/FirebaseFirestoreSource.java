package com.example.mealsplanner.data.source.remote.firestore;

import androidx.annotation.NonNull;

import com.example.mealsplanner.data.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseFirestoreSource {

    private static final String USERS_COLLECTION = "users";
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public void createUser(@NonNull User user,
                           @NonNull FirestoreCallback callback) {

        firestore.collection(USERS_COLLECTION)
                .document(user.getUid())
                .set(user)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(callback::onFailure);
    }

    public void exists(@NonNull String uid,
                       @NonNull ExistsCallback callback) {

        firestore.collection(USERS_COLLECTION)
                .document(uid)
                .get()
                .addOnSuccessListener(snapshot ->
                        callback.onResult(snapshot.exists())
                )
                .addOnFailureListener(callback::onFailure);
    }

    public void existsByEmail(@NonNull String email,
                              @NonNull ExistsCallback callback) {
        firestore.collection(USERS_COLLECTION)
                .whereEqualTo("email", email)
                .limit(1)
                .get()
                .addOnSuccessListener(querySnapshot ->
                        callback.onResult(!querySnapshot.isEmpty())
                )
                .addOnFailureListener(callback::onFailure);
    }

    public interface FirestoreCallback {
        void onSuccess();

        void onFailure(@NonNull Exception e);
    }

    public interface ExistsCallback {
        void onResult(boolean exists);

        void onFailure(@NonNull Exception e);
    }
}

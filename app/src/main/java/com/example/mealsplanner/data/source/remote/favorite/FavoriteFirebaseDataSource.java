package com.example.mealsplanner.data.source.remote.favorite;

import com.example.mealsplanner.data.domain.model.Meal;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FavoriteFirebaseDataSource
        implements FavoriteRemoteDataSource {

    private final FirebaseFirestore firestore;

    public FavoriteFirebaseDataSource() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    private CollectionReference favoritesRef(String userId) {
        return firestore
                .collection("users")
                .document(userId)
                .collection("favorites");
    }


    @Override
    public Completable addFavorite(String userId, Meal meal) {
        return Completable.create(emitter ->
                favoritesRef(userId)
                        .document(meal.getId())
                        .set(meal)
                        .addOnSuccessListener(unused -> {
                            if (!emitter.isDisposed()) {
                                emitter.onComplete();
                            }
                        })
                        .addOnFailureListener(e -> {
                            if (!emitter.isDisposed()) {
                                emitter.onError(e);
                            }
                        })
        );
    }

    @Override
    public Completable removeFavorite(String userId, String mealId) {
        return Completable.create(emitter ->
                favoritesRef(userId)
                        .document(mealId)
                        .delete()
                        .addOnSuccessListener(unused -> {
                            if (!emitter.isDisposed()) {
                                emitter.onComplete();
                            }
                        })
                        .addOnFailureListener(e -> {
                            if (!emitter.isDisposed()) {
                                emitter.onError(e);
                            }
                        })
        );
    }

    @Override
    public Single<List<Meal>> getFavorites(String userId) {
        return Single.create(emitter ->
                favoritesRef(userId)
                        .get()
                        .addOnSuccessListener(snapshot -> {
                            List<Meal> meals =
                                    snapshot.toObjects(Meal.class);

                            if (!emitter.isDisposed()) {
                                emitter.onSuccess(meals);
                            }
                        })
                        .addOnFailureListener(e -> {
                            if (!emitter.isDisposed()) {
                                emitter.onError(e);
                            }
                        })
        );
    }
}

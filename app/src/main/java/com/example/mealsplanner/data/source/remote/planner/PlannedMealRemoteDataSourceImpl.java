package com.example.mealsplanner.data.source.remote.planner;

import com.example.mealsplanner.data.domain.entity.PlannedMealEntity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class PlannedMealRemoteDataSourceImpl implements PlannedMealRemoteDataSource {

    private final FirebaseFirestore firestore;

    public PlannedMealRemoteDataSourceImpl() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public Completable insertPlannedMeal(String userId, PlannedMealEntity meal) {
        return Completable.create(emitter -> {

            String documentId = meal.getDay() + "_" + meal.getMealId();

            firestore.collection("users")
                    .document(userId)
                    .collection("plannedMeals")
                    .document(documentId)
                    .set(meal)
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Completable deletePlannedMeal(String userId, PlannedMealEntity meal) {
        return Completable.create(emitter -> {

            String documentId = meal.getDay() + "_" + meal.getMealId();

            firestore.collection("users")
                    .document(userId)
                    .collection("plannedMeals")
                    .document(documentId)
                    .delete()
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Single<List<PlannedMealEntity>> getMealsByDay(String userId, String day) {
        return Single.create(emitter -> {

            firestore.collection("users")
                    .document(userId)
                    .collection("plannedMeals")
                    .whereEqualTo("day", day)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<PlannedMealEntity> meals =
                                queryDocumentSnapshots.toObjects(PlannedMealEntity.class);
                        emitter.onSuccess(meals);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }
}

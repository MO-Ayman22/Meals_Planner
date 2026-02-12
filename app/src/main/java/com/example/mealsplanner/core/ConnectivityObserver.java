package com.example.mealsplanner.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class ConnectivityObserver {

    private static volatile ConnectivityObserver instance;
    private final BehaviorSubject<Boolean> connectivitySubject = BehaviorSubject.create();

    private ConnectivityObserver(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest request = new NetworkRequest.Builder().build();

        cm.registerNetworkCallback(request, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                connectivitySubject.onNext(true);
            }

            @Override
            public void onLost(@NonNull Network network) {
                connectivitySubject.onNext(false);
            }
        });
    }

    public static ConnectivityObserver getInstance(Context context) {
        if (instance == null) {
            synchronized (ConnectivityObserver.class) {
                if (instance == null) {
                    instance = new ConnectivityObserver(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public Observable<Boolean> observe() {
        return connectivitySubject.hide();
    }
}

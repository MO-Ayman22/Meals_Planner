package com.example.mealsplanner.data.source.remote.auth;

import static com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.CancellationSignal;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialCancellationException;
import androidx.credentials.exceptions.GetCredentialException;

import com.example.mealsplanner.R;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AuthRemoteDataSourceImpl implements AuthRemoteDataSource {

    private final FirebaseAuth auth;
    private final CredentialManager credentialManager;
    private final GetCredentialRequest credentialRequest;
    private final Application app;

    public AuthRemoteDataSourceImpl(@NonNull Application app) {
        this.app = app;
        this.auth = FirebaseAuth.getInstance();
        credentialManager = CredentialManager.create(app);

        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false).setServerClientId(app.getString(R.string.default_web_client_id)).build();

        credentialRequest = new GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build();
    }

    @Override
    public Single<FirebaseUser> signInWithGoogle(Activity activity) {
        return Single.create(emitter -> {
            credentialManager.getCredentialAsync(
                    activity,
                    credentialRequest,
                    new CancellationSignal(),
                    ContextCompat.getMainExecutor(activity),
                    new CredentialManagerCallback<>() {
                        @SuppressLint("CheckResult")
                        @Override
                        public void onResult(GetCredentialResponse result) {
                            try {
                                handleGoogleCredential(result.getCredential())
                                        .subscribe(emitter::onSuccess, emitter::onError);
                            } catch (Exception e) {
                                emitter.onError(e);
                            }
                        }

                        @Override
                        public void onError(@NonNull GetCredentialException e) {
                            if (e instanceof GetCredentialCancellationException) {
                                emitter.onError(new Exception("Cancelled by user"));
                            } else {
                                emitter.onError(e);
                            }
                        }
                    }
            );
        });
    }

    private Single<FirebaseUser> handleGoogleCredential(@NonNull Credential credential) {
        return Single.create(emitter -> {

            if (!(credential instanceof CustomCredential customCredential) ||
                    !TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(customCredential.getType())) {
                emitter.onError(new IllegalStateException("Invalid Google credential"));
                return;
            }

            GoogleIdTokenCredential googleCred = GoogleIdTokenCredential.createFrom(customCredential.getData());

            AuthCredential firebaseCred = GoogleAuthProvider.getCredential(googleCred.getIdToken(), null);

            auth.signInWithCredential(firebaseCred)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && auth.getCurrentUser() != null) {
                            emitter.onSuccess(auth.getCurrentUser());
                        } else {
                            emitter.onError(task.getException() != null
                                    ? task.getException()
                                    : new IllegalStateException("Sign-in failed"));
                        }
                    });
        });
    }

    @Override
    public Single<FirebaseUser> signInWithEmail(String email, String password) {
        return Single.create(emitter ->
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful() && auth.getCurrentUser() != null) {
                                emitter.onSuccess(auth.getCurrentUser());
                            } else {
                                emitter.onError(task.getException() != null
                                        ? task.getException()
                                        : new IllegalStateException("Sign-in failed"));
                            }
                        })
        );
    }

    @Override
    public Single<FirebaseUser> register(String email, String password, String name) {
        return Single.create(emitter ->
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                emitter.onError(task.getException());
                                return;
                            }

                            FirebaseUser user = auth.getCurrentUser();
                            if (user == null) {
                                emitter.onError(new IllegalStateException("User is null"));
                                return;
                            }

                            user.updateProfile(new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name).build())
                                    .addOnCompleteListener(updateTask -> {
                                        if (updateTask.isSuccessful()) emitter.onSuccess(user);
                                        else emitter.onError(updateTask.getException());
                                    });
                        })
        );
    }

    @Override
    public Completable resetPassword(String email) {
        return Completable.create(emitter ->
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) emitter.onComplete();
                            else emitter.onError(task.getException());
                        })
        );
    }

}

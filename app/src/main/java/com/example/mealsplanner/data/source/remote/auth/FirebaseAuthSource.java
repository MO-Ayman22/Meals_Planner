package com.example.mealsplanner.data.source.remote.auth;

import static com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL;

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

public class FirebaseAuthSource {

    private final FirebaseAuth auth;
    private final CredentialManager credentialManager;
    private final GetCredentialRequest credentialRequest;
    private final Application app;

    public FirebaseAuthSource(@NonNull Application app) {
        this.app = app;
        this.auth = FirebaseAuth.getInstance();
        credentialManager = CredentialManager.create(app);

        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false).setServerClientId(app.getString(R.string.default_web_client_id)).build();

        credentialRequest = new GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build();
    }

    public void signInWithGoogle(@NonNull AuthCallback callback) {
        credentialManager.getCredentialAsync(app, credentialRequest, new CancellationSignal(), ContextCompat.getMainExecutor(app), new CredentialManagerCallback<>() {

            @Override
            public void onResult(GetCredentialResponse result) {
                handleGoogleCredential(result.getCredential(), callback);
            }

            @Override
            public void onError(@NonNull GetCredentialException e) {
                if (e instanceof GetCredentialCancellationException) callback.onCancelled();
                else callback.onFailure(e);
            }
        });
    }

    private void handleGoogleCredential(@NonNull Credential credential, @NonNull AuthCallback callback) {

        if (!(credential instanceof CustomCredential customCredential) || !TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(customCredential.getType())) {
            callback.onFailure(new IllegalStateException("Invalid Google credential"));
            return;
        }

        GoogleIdTokenCredential googleCred = GoogleIdTokenCredential.createFrom(customCredential.getData());

        AuthCredential firebaseCred = GoogleAuthProvider.getCredential(googleCred.getIdToken(), null);

        auth.signInWithCredential(firebaseCred).addOnCompleteListener(task -> {
            if (task.isSuccessful()) callback.onSuccess(auth.getCurrentUser());
            else callback.onFailure(task.getException());
        });
    }

    public void signInWithEmail(String email, String password, @NonNull AuthCallback callback) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) callback.onSuccess(auth.getCurrentUser());
            else callback.onFailure(task.getException());
        });
    }

    public void register(String email, String password, String name, @NonNull AuthCallback callback) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                callback.onFailure(task.getException());
                return;
            }

            FirebaseUser user = auth.getCurrentUser();
            if (user == null) {
                callback.onFailure(new IllegalStateException("User is null"));
                return;
            }

            user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(name).build()).addOnCompleteListener(updateTask -> {
                if (updateTask.isSuccessful()) callback.onSuccess(user);
                else callback.onFailure(updateTask.getException());
            });
        });
    }

    public void resetPassword(String email, @NonNull AuthCallback callback) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) callback.onSuccess(null);
            else callback.onFailure(task.getException());
        });
    }

    public interface AuthCallback {
        void onSuccess(FirebaseUser user);

        void onFailure(@NonNull Exception e);

        void onCancelled();
    }

}

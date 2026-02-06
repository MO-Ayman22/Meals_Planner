package com.example.mealsplanner.presentation.auth.login;

import android.app.Application;

import com.example.mealsplanner.data.model.User;
import com.example.mealsplanner.data.repository.AuthRepository;
import com.example.mealsplanner.data.repository.UserRepository;
import com.example.mealsplanner.data.source.remote.auth.FirebaseAuthSource;
import com.example.mealsplanner.data.source.remote.firestore.UserRemoteDataSource;
import com.example.mealsplanner.util.ValidationUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View view;
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public LoginPresenter(Application app, LoginContract.View view) {
        this.view = view;
        this.authRepository = new AuthRepository(new FirebaseAuthSource(app));
        this.userRepository = new UserRepository(new UserRemoteDataSource());
    }

    @Override
    public void onLoginClicked(String email, String password) {

        view.showLoginButtonLoading();

        if (!validateInputs(email, password)) return;

        Disposable disposable = userRepository.existsByEmail(email).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        exists -> {
                            if (exists) {
                                login(email, password);
                            } else {
                                view.hideLoginButtonLoading();
                                view.showEmailError("Email does not exist");
                            }
                        },
                        throwable -> {
                            view.hideLoginButtonLoading();
                            view.onLoginError(throwable.getMessage());
                        });
        disposables.add(disposable);
    }

    private void login(String email, String password) {
        view.showLoginButtonLoading();
        Disposable disposable = authRepository.loginWithEmail(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        firebaseUser -> {
                            view.hideLoginButtonLoading();
                            view.onLoginSuccess();
                        },
                        throwable -> {
                            view.hideLoginButtonLoading();
                            view.showPasswordError("Invalid password");
                        }
                );
        disposables.add(disposable);
    }

    @Override
    public void onGoogleLoginClicked() {
        view.showGoogleButtonLoading();

        Disposable d =
                authRepository.loginWithGoogle()
                        .subscribeOn(Schedulers.io())
                        .flatMap(firebaseUser ->
                                userRepository.exists(firebaseUser.getUid())
                                        .flatMap(exists -> {
                                            if (exists) {
                                                return Single.just(firebaseUser);
                                            } else {
                                                User user = new User(
                                                        firebaseUser.getUid(),
                                                        firebaseUser.getDisplayName(),
                                                        firebaseUser.getEmail()
                                                );
                                                return userRepository.create(user)
                                                        .andThen(Single.just(firebaseUser));
                                            }
                                        })
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user -> {
                                    view.hideGoogleButtonLoading();
                                    view.onLoginSuccess();
                                },
                                throwable -> {
                                    view.hideGoogleButtonLoading();
                                    view.onLoginError(throwable.getMessage());
                                }
                        );

        disposables.add(d);
    }

    private boolean validateInputs(String email, String password) {
        String emailError = ValidationUtil.validateEmail(email);
        if (emailError != null) {
            view.hideLoginButtonLoading();
            view.showEmailError(emailError);
            return false;
        }

        String passwordError = ValidationUtil.validatePassword(password);
        if (passwordError != null) {
            view.hideLoginButtonLoading();
            view.showPasswordError(passwordError);
            return false;
        }
        return true;
    }

    @Override
    public void clear() {
        disposables.clear();
    }
}

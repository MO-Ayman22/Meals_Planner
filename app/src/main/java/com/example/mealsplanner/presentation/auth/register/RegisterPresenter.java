package com.example.mealsplanner.presentation.auth.register;

import com.example.mealsplanner.core.BaseApplication;
import com.example.mealsplanner.core.SessionManager;
import com.example.mealsplanner.data.domain.model.User;
import com.example.mealsplanner.data.repository.AuthRepository;
import com.example.mealsplanner.data.repository.UserRepository;
import com.example.mealsplanner.util.ValidationUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterPresenter implements RegisterContract.Presenter {
    private final RegisterContract.View view;
    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    private final SessionManager sessionManager;
    private final CompositeDisposable disposables = new CompositeDisposable();


    public RegisterPresenter(AuthRepository authRepository, UserRepository userRepository, RegisterContract.View view) {

        this.view = view;
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.sessionManager = BaseApplication.getInstance().session();
    }

    @Override
    public void onRegisterClicked(String name, String email, String password, String confirmPassword, boolean isChecked) {
        view.showRegisterButtonLoading();
        if (!validateInputs(name, email, password, confirmPassword, isChecked)) return;

        Disposable disposable = userRepository.existsByEmail(email).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        exists -> {
                            if (exists) {
                                view.hideRegisterButtonLoading();
                                view.showEmailError("Email already exists");
                            } else {
                                register(name, email, password);
                            }
                        },
                        throwable -> {
                            view.hideRegisterButtonLoading();
                            view.onRegisterError(throwable.getMessage());

                        });

        disposables.add(disposable);
    }

    private void register(String name, String email, String password) {
        Disposable disposable = authRepository.register(email, password, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        firebaseUser -> {
                            view.hideRegisterButtonLoading();
                            sessionManager.saveLoginSession(firebaseUser.getUid());
                            view.onRegisterSuccess();
                        }
                        , throwable -> {
                            view.hideRegisterButtonLoading();
                            view.onRegisterError(throwable.getMessage());
                        });
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
                                    sessionManager.saveLoginSession(user.getUid());
                                    view.onRegisterSuccess();
                                },
                                throwable -> {
                                    view.hideGoogleButtonLoading();
                                    view.onRegisterError(throwable.getMessage());
                                }
                        );

        disposables.add(d);
    }

    private boolean validateInputs(String name, String email, String password, String confirmPassword, boolean isChecked) {
        String nameError = ValidationUtil.validateName(name);
        if (nameError != null) {
            view.hideRegisterButtonLoading();
            view.showNameError(nameError);
            return false;
        }

        String emailError = ValidationUtil.validateEmail(email);
        if (emailError != null) {
            view.hideRegisterButtonLoading();
            view.showEmailError(emailError);
            return false;
        }

        String passwordError = ValidationUtil.validatePassword(password);
        if (passwordError != null) {
            view.hideRegisterButtonLoading();
            view.showPasswordError(passwordError);
            return false;
        }

        String confirmPasswordError = ValidationUtil.validateConfirmPassword(password, confirmPassword);
        if (confirmPasswordError != null) {
            view.hideRegisterButtonLoading();
            view.showConfirmPasswordError(confirmPasswordError);
            return false;
        }

        String termsAndConditionsError = ValidationUtil.validateTermsAndConditions(isChecked);
        if (termsAndConditionsError != null) {
            view.hideRegisterButtonLoading();
            view.showTermsAndConditionsError(termsAndConditionsError);
            return false;
        }

        return true;
    }

    @Override
    public void clear() {
        disposables.clear();
    }

}

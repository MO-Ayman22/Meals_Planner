package com.example.mealsplanner.presentation.auth.forgetpass;

import android.app.Application;

import com.example.mealsplanner.data.repository.AuthRepository;
import com.example.mealsplanner.data.repository.UserRepository;
import com.example.mealsplanner.data.source.remote.auth.FirebaseAuthSource;
import com.example.mealsplanner.data.source.remote.firestore.UserRemoteDataSource;
import com.example.mealsplanner.util.ValidationUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ForgetPasswordPresenter implements ForgetPasswordContract.Presenter {

    private final ForgetPasswordContract.View view;
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();


    public ForgetPasswordPresenter(Application app, ForgetPasswordContract.View view) {
        this.view = view;
        this.authRepository = new AuthRepository(new FirebaseAuthSource(app));
        this.userRepository = new UserRepository(new UserRemoteDataSource());
    }


    @Override
    public void onResetPasswordClicked(String email) {
        String emailError = ValidationUtil.validateEmail(email);
        if (emailError != null) {
            view.showEmailError(emailError);
            return;
        }
        Disposable disposable = userRepository.existsByEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        exists -> {
                            if (exists) {
                                view.showResetPasswordButtonLoading();
                                Disposable d = authRepository.resetPassword(email)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                () -> {
                                                    view.hideResetPasswordButtonLoading();
                                                    view.onResetPasswordSuccess();
                                                }
                                                , throwable -> {
                                                    view.hideResetPasswordButtonLoading();
                                                    view.onResetPasswordError(throwable.getMessage());
                                                });
                                disposables.add(d);
                            } else {
                                view.showEmailError("Email does not exist");
                            }
                        },
                        throwable -> {
                            view.hideResetPasswordButtonLoading();
                            view.onResetPasswordError(throwable.getMessage());
                        });
        disposables.add(disposable);
    }

    @Override
    public void clear() {
        disposables.clear();
    }
}

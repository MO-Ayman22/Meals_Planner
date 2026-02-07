package com.example.mealsplanner.presentation.auth.forgetpass;

import com.example.mealsplanner.data.repository.AuthRepository;
import com.example.mealsplanner.data.repository.UserRepository;
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


    public ForgetPasswordPresenter(AuthRepository authRepository, UserRepository userRepository, ForgetPasswordContract.View view) {

        this.view = view;
        this.authRepository = authRepository;
        this.userRepository = userRepository;
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

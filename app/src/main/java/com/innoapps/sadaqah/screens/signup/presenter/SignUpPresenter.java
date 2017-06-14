package com.innoapps.sadaqah.screens.signup.presenter;

import android.app.Activity;

import com.innoapps.sadaqah.screens.signup.view.SignUpView;

/**
 * Created by ankit on 6/12/2017.
 */

public interface SignUpPresenter {

    void validateSignUp(Activity activity, SignUpView signUpView, String name, String email, String password, String profile_image);

}

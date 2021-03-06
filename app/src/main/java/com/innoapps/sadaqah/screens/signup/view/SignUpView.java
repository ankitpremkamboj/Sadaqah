package com.innoapps.sadaqah.screens.signup.view;

/**
 * Created by ankit on 6/12/2017.
 */

public interface SignUpView {


    void onNameError(String name);

    void onEmailError(String name);

    void onValidEmailError(String name);

    void onPasswordError(String name);

    void onSignUpImageError(String profileImage);

    void onSignUpInternetError();

    void onSignUpSuccessful(String message);

    void onSignUpUnSuccessful(String msg);
}

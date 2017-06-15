package com.innoapps.sadaqah.screens.editprofile.view;

/**
 * Created by ankit on 6/14/2017.
 */

public interface EditProfileView {


    void onNameError(String name);

    void onEmailError(String name);

    void onValidEmailError(String name);

    void onPasswordError(String name);

    void onSignUpImageError(String profileImage);

    void onInternetError();

    void onEditProfileSuccessful(String message);

    void onSignUpUnSuccessful(String msg);
}

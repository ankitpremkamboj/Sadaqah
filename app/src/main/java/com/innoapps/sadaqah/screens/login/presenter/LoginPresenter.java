package com.innoapps.sadaqah.screens.login.presenter;

import android.app.Activity;

import com.innoapps.sadaqah.screens.login.view.LoginView;

/**
 * Created by ankit on 6/13/2017.
 */

public interface LoginPresenter {

    void validateLogin(String email, String password, Activity activity, LoginView loginView);
}

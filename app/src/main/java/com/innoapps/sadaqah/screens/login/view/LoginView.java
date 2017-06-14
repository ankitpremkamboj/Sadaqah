package com.innoapps.sadaqah.screens.login.view;

import com.innoapps.sadaqah.screens.login.model.LoginModel;

/**
 * Created by ankit on 6/13/2017.
 */

public interface LoginView {


    void onEmailError(String msg);

    void onPasswordError(String msg);

    void onLoginSuccess(LoginModel.Data data);

    void onLoginUnSuccessful(String msg);

    void onLoginInternetError();

}

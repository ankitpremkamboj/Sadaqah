package com.innoapps.sadaqah.screens.login.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.helper.Progress;
import com.innoapps.sadaqah.requestresponse.ApiAdapter;
import com.innoapps.sadaqah.screens.login.model.LoginModel;
import com.innoapps.sadaqah.screens.login.view.LoginView;
import com.innoapps.sadaqah.utils.UserSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 6/13/2017.
 */

public class LoginPresenterImpl implements LoginPresenter {


    @Override
    public void validateLogin(String email, String password, Activity activity, LoginView loginView) {

        if (validateCredential(email, password, activity, loginView)) {

            mLoginRecuriter(email, password, activity, loginView);
        }

    }

    private void mLoginRecuriter(String email, String password, Activity activity, LoginView loginView) {

        try {
            ApiAdapter.getInstance(activity);
            callLoginMethod(email, password, activity, loginView);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }

    private void callLoginMethod(String email, String password, final Activity activity, final LoginView loginView) {


        Progress.start(activity);


        Call<LoginModel> callLogin;

        callLogin = ApiAdapter.getApiService().login(email, password);

        callLogin.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    LoginModel loginItem = response.body();

                    String message = loginItem.getMessage();
                    int code = loginItem.getCode();

                    Log.e("login code :", ":" + code);

                    if (loginItem.getCode() == 0) {
                        Log.e("login code1111 :", ":" + code);
                        //getting data from Login Item
                        LoginModel.Data loginResult = loginItem.getData();
                        UserSession userSession = new UserSession(activity);
                        userSession.createUserID(loginResult.getUserid());
                        loginView.onLoginSuccess(loginResult);

                    } else {
                        loginView.onLoginUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    loginView.onLoginUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Progress.stop();
                loginView.onLoginUnSuccessful(activity.getString(R.string.server_error));

            }

        });
    }

    public boolean validateCredential(String email, String pwd, Activity activity, LoginView loginView) {

        if (TextUtils.isEmpty(email)) {
            loginView.onEmailError(activity.getString(R.string.empty_email));
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
            loginView.onPasswordError(activity.getString(R.string.empty_password));
            return false;
        } else {
            return true;
        }
    }
}

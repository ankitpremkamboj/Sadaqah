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

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 6/13/2017.
 */

public class LoginPresenterImpl implements LoginPresenter {
    Activity activity;
    LoginView loginView;


    @Override
    public void validateLogin(String email, String password, Activity activity, LoginView loginView) {
        this.activity = activity;
        this.loginView = loginView;
        if (validateCredential(email, password, activity, loginView)) {

            mLoginRecuriter(email, password, activity, loginView);
        }

    }

    @Override
    public void socialLogin(String name, String email, String provider, String image,Activity activity,LoginView loginView) {
        try {
            this.activity = activity;
            this.loginView = loginView;
            ApiAdapter.getInstance(activity);
            callSocialLoginMethod(name, email, provider, image, activity, loginView);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();
            loginView.onLoginInternetError();

        }
    }

    private void callSocialLoginMethod(String name, String email, String provider, String image, final Activity activity, final LoginView loginView) {


        Progress.start(activity);


        Call<LoginModel> callLogin;


        File file;
        RequestBody requestBody;
        MultipartBody.Part imageFileBody;
        //prepare image file


        RequestBody nameRequest = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody providerRequest = RequestBody.create(MediaType.parse("text/plain"), provider);
        RequestBody emailRequest = RequestBody.create(MediaType.parse("text/plain"), email);

        if (image.equalsIgnoreCase("")) {
            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            imageFileBody = MultipartBody.Part.createFormData("image", "path", requestBody);
        } else {
            file = new File(image);
            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            imageFileBody = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        }


        callLogin = ApiAdapter.getApiService().socialLogin(nameRequest, emailRequest, providerRequest, imageFileBody);

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

    private void mLoginRecuriter(String email, String password, Activity activity, LoginView loginView) {

        try {
            ApiAdapter.getInstance(activity);
            callLoginMethod(email, password, activity, loginView);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();
            loginView.onLoginInternetError();

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

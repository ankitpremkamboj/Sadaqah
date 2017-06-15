package com.innoapps.sadaqah.screens.signup.presenter;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.helper.Progress;
import com.innoapps.sadaqah.requestresponse.ApiAdapter;
import com.innoapps.sadaqah.screens.signup.model.SignUpModel;
import com.innoapps.sadaqah.screens.signup.view.SignUpView;
import com.innoapps.sadaqah.utils.UserSession;
import com.innoapps.sadaqah.utils.Utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 6/12/2017.
 */

public class SignUpPresenterImpl implements SignUpPresenter {
    @Override
    public void validateSignUp(Activity activity, SignUpView signUpView, String name, String email, String password, String profile_image) {

        if (validateCredential(activity, signUpView, name, email, password, profile_image)) {
            //  name, email, mobile, company_name, password, cameraImageFilePath
            callRegisterApi(activity, signUpView, name, email, password, profile_image);
        }

    }

    private void callRegisterApi(Activity activity, SignUpView signUpView, String name, String email, String password, String profile_image) {

        try {
            ApiAdapter.getInstance(activity);
            callSignupMethod(activity, signUpView, name, email, password, profile_image);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();
            signUpView.onSignUpInternetError();

        }
    }

    private void callSignupMethod(final Activity activity, final SignUpView signUpView, String name, String email, String password, String profile_image) {

        Progress.start(activity);


        Call<SignUpModel> callLogin;


        File file;
        RequestBody requestBody;
        MultipartBody.Part imageFileBody;
        //prepare image file


        RequestBody nameRequest = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody passwordRequest = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody emailRequest = RequestBody.create(MediaType.parse("text/plain"), email);

        if (profile_image.equalsIgnoreCase("")) {
            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            imageFileBody = MultipartBody.Part.createFormData("profile_pic", "path", requestBody);
        } else {
            file = new File(profile_image);
            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            imageFileBody = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        }


        callLogin = ApiAdapter.getApiService().signUp(nameRequest, emailRequest, passwordRequest, imageFileBody);

        callLogin.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    SignUpModel userProfileModel = response.body();

                    String message = userProfileModel.getMessage();

                    if (userProfileModel.getCode() == 0) {

                        UserSession userSession = new UserSession(activity);
                        //  userSession.createUserSession(loginResult.getUserId(), loginResult.getName(), loginResult.getEmail(), loginResult.getMobile(), loginResult.getTokenId(), loginResult.getImage());
                        userSession.createUserID(String.valueOf(userProfileModel.getData().getUserid()));
                        //   onLoginFinshedListener.onLoginSuccess(loginResult);
                        signUpView.onSignUpSuccessful(message);


                    } else {
                        signUpView.onSignUpUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    signUpView.onSignUpUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                Progress.stop();
                signUpView.onSignUpUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }

    public boolean validateCredential(Activity activity, SignUpView registerView, String name, String email, String password, String profile_image) {

        if (TextUtils.isEmpty(name)) {
            registerView.onNameError(activity.getString(R.string.empty_name));
            return false;
        } else if (TextUtils.isEmpty(email)) {
            registerView.onEmailError(activity.getString(R.string.empty_email));

            return false;
        } else if (!Utils.emailValidation(email)) {
            registerView.onValidEmailError(activity.getString(R.string.invalid_email));

            return false;
        } else if (TextUtils.isEmpty(password)) {
            registerView.onPasswordError(activity.getString(R.string.empty_password));
            return false;
        } else {
            return true;
        }
    }

}

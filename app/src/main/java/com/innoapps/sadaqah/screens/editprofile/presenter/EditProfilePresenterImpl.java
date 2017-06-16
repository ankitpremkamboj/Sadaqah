package com.innoapps.sadaqah.screens.editprofile.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.helper.Progress;
import com.innoapps.sadaqah.requestresponse.ApiAdapter;
import com.innoapps.sadaqah.screens.editprofile.model.EditProfileModel;
import com.innoapps.sadaqah.screens.editprofile.view.EditProfileView;
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
 * Created by ankit on 6/14/2017.
 */

public class EditProfilePresenterImpl implements EditProfilePresenter {


    @Override
    public void validateProfile(Activity activity, EditProfileView editProfileView, String name, String email, String password, String profile_image) {
        if (validateCredential(activity, editProfileView, name, email, password, profile_image)) {
            //  name, email, mobile, company_name, password, cameraImageFilePath
            callRegisterApi(activity, editProfileView, name, email, password, profile_image);
        }
    }

    private void callRegisterApi(Activity activity, EditProfileView editProfileView, String name, String email, String password, String profile_image) {

        try {
            ApiAdapter.getInstance(activity);
            callSignupMethod(activity, editProfileView, name, email, password, profile_image);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();
            editProfileView.onInternetError();

        }
    }

    private void callSignupMethod(final Activity activity, final EditProfileView signUpView, String name, String email, String password, String profile_image) {

        Progress.start(activity);
        UserSession userSession = new UserSession(activity);

        Call<EditProfileModel> callLogin;


        File file;
        RequestBody requestBody;
        MultipartBody.Part imageFileBody;
        //prepare image file

        RequestBody userIdRequest = RequestBody.create(MediaType.parse("text/plane"), userSession.getUserID());
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


        callLogin = ApiAdapter.getApiService().updateProfile(nameRequest, emailRequest, imageFileBody, userIdRequest);

        callLogin.enqueue(new Callback<EditProfileModel>() {
            @Override
            public void onResponse(Call<EditProfileModel> call, Response<EditProfileModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    EditProfileModel userProfileModel = response.body();

                    String message = userProfileModel.getMessage();

                    if (userProfileModel.getCode() == 0) {

                        signUpView.onEditProfileSuccessful(message);


                    } else {
                        signUpView.onSignUpUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    signUpView.onSignUpUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<EditProfileModel> call, Throwable t) {
                Progress.stop();
                signUpView.onSignUpUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }

    public boolean validateCredential(Activity activity, EditProfileView editProfileView, String name, String email, String password, String profile_image) {

        if (TextUtils.isEmpty(name)) {
            editProfileView.onNameError(activity.getString(R.string.empty_name));
            return false;
        } else if (TextUtils.isEmpty(email)) {
            editProfileView.onEmailError(activity.getString(R.string.empty_email));

            return false;
        } else if (!Utils.emailValidation(email)) {
            editProfileView.onValidEmailError(activity.getString(R.string.invalid_email));

            return false;
        }  else {
            return true;
        }
    }
}

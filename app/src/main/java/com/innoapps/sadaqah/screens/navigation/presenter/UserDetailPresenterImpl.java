package com.innoapps.sadaqah.screens.navigation.presenter;

import android.app.Activity;
import android.util.Log;

import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.helper.Progress;
import com.innoapps.sadaqah.requestresponse.ApiAdapter;
import com.innoapps.sadaqah.screens.navigation.model.UserDetailModel;
import com.innoapps.sadaqah.screens.navigation.view.UserDetailView;
import com.innoapps.sadaqah.utils.UserSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 6/14/2017.
 */

public class UserDetailPresenterImpl implements UserDetailPresenter {
    Activity activity;
    UserDetailView userDetailView;

    @Override
    public void gettingUserDetails(Activity activity, UserDetailView userDetailView, String userId) {

        try {
            this.activity = activity;
            this.userDetailView = userDetailView;

            ApiAdapter.getInstance(activity);
            callUserDetailsMethod(userId);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }


    }

    private void callUserDetailsMethod(String userId) {


        Progress.start(activity);


        Call<UserDetailModel> callLogin;

        callLogin = ApiAdapter.getApiService().getUserDetails(userId);

        callLogin.enqueue(new Callback<UserDetailModel>() {
            @Override
            public void onResponse(Call<UserDetailModel> call, Response<UserDetailModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    UserDetailModel loginItem = response.body();

                    String message = loginItem.getMessage();
                    int code = loginItem.getCode();


                    if (loginItem.getCode() == 0) {

                        //getting data from Login Item
                        UserDetailModel.Data loginResult = loginItem.getData();
                        userDetailView.getUserDetailsSuccessfull(loginResult);

                    } else {
                        userDetailView.getUserDetailsUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    userDetailView.getUserDetailsUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<UserDetailModel> call, Throwable t) {
                Progress.stop();
                userDetailView.getUserDetailsUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }
}

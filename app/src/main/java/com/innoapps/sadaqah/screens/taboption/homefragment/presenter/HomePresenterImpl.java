package com.innoapps.sadaqah.screens.taboption.homefragment.presenter;

import android.app.Activity;

import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.helper.Progress;
import com.innoapps.sadaqah.requestresponse.ApiAdapter;
import com.innoapps.sadaqah.screens.taboption.homefragment.model.HomeModel;
import com.innoapps.sadaqah.screens.taboption.homefragment.view.HomeView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 6/9/2017.
 */

public class HomePresenterImpl implements HomePresenter {
    Activity activity;
    HomeView homeView;

    @Override
    public void callGetHomeList(String userID, Activity activity, HomeView homeView) {

        try {
            this.homeView = homeView;
            this.activity = activity;
            ApiAdapter.getInstance(activity);
            getAllHomeList(userID);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }

    }

    private void getAllHomeList(String userID) {

        Progress.start(activity);
        Call<HomeModel> callEvent;


        callEvent = ApiAdapter.getApiService().homeList("aaa");

        callEvent.enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    HomeModel homeModel = response.body();

                    ArrayList<HomeModel.Datum> homeLists = homeModel.getData();

                    String message = homeModel.getMessage();


                    if (homeModel.getCode() == 0) {
                        homeView.getHomeListSuccessfull(homeLists);

                    } else {
                        homeView.getHomeListUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    homeView.getHomeListUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                Progress.stop();
                homeView.getHomeListUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }
}

package com.innoapps.sadaqah.screens.history.presenter;

import android.app.Activity;

import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.helper.Progress;
import com.innoapps.sadaqah.requestresponse.ApiAdapter;
import com.innoapps.sadaqah.screens.history.model.HistoryModel;
import com.innoapps.sadaqah.screens.history.view.HistoryView;
import com.innoapps.sadaqah.screens.taboption.homefragment.model.HomeModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 7/3/2017.
 */

public class HistoryPresenterImpl implements  HistoryPresenter{

    HistoryView historyView;
    Activity activity;
    @Override
    public void callGetHistoryList(String userID, Activity activity, HistoryView historyView) {
        try {
            this.historyView = historyView;
            this.activity = activity;
            ApiAdapter.getInstance(activity);
            getAllHistoryList(userID);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();
            historyView.historyInternetError();
        }
    }

    private void getAllHistoryList(String userID) {

        Progress.start(activity);
        Call<HistoryModel> callHistory;


        callHistory = ApiAdapter.getApiService().getHistoryDetails(userID);

        callHistory.enqueue(new Callback<HistoryModel>() {
            @Override
            public void onResponse(Call<HistoryModel> call, Response<HistoryModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    HistoryModel  historyModel = response.body();

                    ArrayList<HistoryModel.Datum> homeLists = historyModel.getData();

                    String message = historyModel.getMessage();


                    if (historyModel.getCode() == 0) {
                        historyView.getHistoryListSuccessful(homeLists);

                    } else {
                        historyView.getHistoryListUnSuccessful(message);
                    }
                } catch (NullPointerException e) {
                    historyView.getHistoryListUnSuccessful(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<HistoryModel> call, Throwable t) {
                Progress.stop();
                historyView.getHistoryListUnSuccessful(activity.getString(R.string.server_error));

            }

        });


    }
}

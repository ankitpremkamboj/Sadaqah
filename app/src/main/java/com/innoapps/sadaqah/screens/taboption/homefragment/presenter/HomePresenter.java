package com.innoapps.sadaqah.screens.taboption.homefragment.presenter;

import android.app.Activity;

import com.innoapps.sadaqah.screens.taboption.homefragment.view.HomeView;

/**
 * Created by ankit on 6/9/2017.
 */

public interface HomePresenter {

    void callGetHomeList(String userID, Activity activity, HomeView homeView);

}
